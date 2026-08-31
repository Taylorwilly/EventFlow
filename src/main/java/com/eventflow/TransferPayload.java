package com.eventflow;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class TransferPayload implements EventPayload {
    private final UUID transactionId;
    private final UUID sourceAccountId;
    private final UUID destinationAccountId;
    private final Currency currency;
    private final BigDecimal amount;

    public TransferPayload(
            UUID transactionId,
            UUID sourceAccountId,
            UUID destinationAccountId,
            Currency currency,
            BigDecimal amount) {

        if (transactionId == null) {
            throw new IllegalArgumentException("Invalid transaction Id");
        }

        if (sourceAccountId == null) {
            throw new IllegalArgumentException("Invalid source account Id");
        }

        if (destinationAccountId == null) {
            throw new IllegalArgumentException("Invalid destination account Id");
        }

        if (sourceAccountId.equals(destinationAccountId)) {
            throw new IllegalArgumentException("The account Ids must be different");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (currency == null) {
            throw new IllegalArgumentException("Invalid currency");
        }

        this.transactionId = transactionId;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.currency = currency;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public UUID getSourceAccountId() {
        return sourceAccountId;
    }

    public UUID getDestinationAccountId() {
        return destinationAccountId;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}