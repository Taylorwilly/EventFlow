package com.eventflow;

import java.math.BigDecimal;
import java.util.Optional;

public class TransferProcessor
        implements EventProcessor<TransferPayload, TransferResult> {
    private AccountStore accountStore;

    public TransferProcessor(AccountStore accountStore) {
        if (accountStore == null) {
            throw new IllegalArgumentException("Account should not be null");
        }
        this.accountStore = accountStore;
    }

    @Override
    public TransferResult process(TransferPayload payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Payload is required");
        }

        Optional<Account> sourceAccount = accountStore.findAccount(payload.getSourceAccountId());
        Optional<Account> destinationAccount = accountStore.findAccount(payload.getDestinationAccountId());

        if (sourceAccount.isEmpty() || destinationAccount.isEmpty()) {
            return new TransferResult(TransferOutcome.REJECTED, TransferRejectionReason.ACCOUNT_NOT_FOUND);
        }
        Account source = sourceAccount.get();
        Account destination = destinationAccount.get();

        if (source.getStatus() == AccountStatus.FROZEN) {
            return new TransferResult(TransferOutcome.REJECTED, TransferRejectionReason.ACCOUNT_FROZEN);
        }

        if (!source.getCurrency().equals(payload.getCurrency())) {
            return new TransferResult(TransferOutcome.REJECTED, TransferRejectionReason.CURRENCY_MISMATCH);
        }

        if (!destination.getCurrency().equals(payload.getCurrency())) {
            return new TransferResult(TransferOutcome.REJECTED, TransferRejectionReason.CURRENCY_MISMATCH);
        }

        if (payload.getAmount().compareTo(source.getBalance()) > 0) {
            return new TransferResult(TransferOutcome.REJECTED, TransferRejectionReason.INSUFFICIENT_FUNDS);
        }

        BigDecimal sourceBalance = source.getBalance();

        source.debit(payload.getAmount());

        try {
            destination.credit(payload.getAmount());
        } catch (RuntimeException e) {
            source.restoreBalance(sourceBalance);
            throw e;
        }

        return new TransferResult(TransferOutcome.APPROVED, null);

    }
}
