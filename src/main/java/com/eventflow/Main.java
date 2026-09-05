package com.eventflow;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        BigDecimal balance1 = BigDecimal.valueOf(500);
        BigDecimal balance2 = BigDecimal.valueOf(100);
        BigDecimal transfer = BigDecimal.valueOf(200);

        Currency currency = Currency.getInstance("CAD");

        AccountStore accountStore = new AccountStore();

        Account sourceAccount = new Account(id1, balance1, currency, AccountStatus.ACTIVE);
        Account destinationAccount = new Account(id2, balance2, currency, AccountStatus.ACTIVE);
        UUID transactionId = UUID.randomUUID();

        accountStore.storeAccount(sourceAccount);
        accountStore.storeAccount(destinationAccount);

        TransferProcessor transferProcessor = new TransferProcessor(accountStore);

        ProcessorRegistration<TransferPayload, TransferResult> registration = new ProcessorRegistration<>(
                TransferPayload.class, transferProcessor);

        Map<EventType, ProcessorRegistration<?, ?>> registry = new HashMap<>();
        registry.put(EventType.TRANSACTION_CREATED, registration);

        EventQueue eventQueue = new EventQueue();
        ResultStore resultStore = new ResultStore();

        Worker worker = new Worker(eventQueue, resultStore, registry);

        TransferPayload payload = new TransferPayload(transactionId, sourceAccount.getId(), destinationAccount.getId(),
                currency, transfer);

        UUID eventId = UUID.randomUUID();
        Event event = new Event(eventId, payload, EventType.TRANSACTION_CREATED);

        eventQueue.addEvent(event);

        try {
            worker.processNextEvent();
        } catch (RuntimeException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        Optional<EventResult> result = resultStore.findResult(eventId);
        System.out.println(sourceAccount.getBalance());
        System.out.println(destinationAccount.getBalance());
        System.out.println(event.getStatus());
        System.out.println(result);

        if (result.isPresent()) {
            EventResult res = result.get();

            if (res instanceof TransferResult) {
                TransferResult transferResult = (TransferResult) res;

                System.out.println(transferResult.getOutcome());
                System.out.println(transferResult.getRejectionReason());
            }
        }
    }
}
