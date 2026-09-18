package com.eventflow.service;

import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.UUID;
import java.math.BigDecimal;

import com.eventflow.EventQueue;
import com.eventflow.SubmissionResponse;
import com.eventflow.TransferPayload;
import com.eventflow.Event;
import com.eventflow.EventType;

@Service
public class TransferService {
    private final EventQueue eventQueue;

    public TransferService(EventQueue eventQueue) {
        if (eventQueue == null) {
            throw new IllegalArgumentException("Event queue is required");
        }
        this.eventQueue = eventQueue;
    }

    public SubmissionResponse submitTransfer(
            UUID sourceAccountId,
            UUID destinationAccountId,
            Currency currency,
            BigDecimal amount) {

        UUID transactionId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();

        TransferPayload payload = new TransferPayload(transactionId, sourceAccountId, destinationAccountId, currency,
                amount);

        Event event = new Event(eventId, payload, EventType.TRANSACTION_CREATED);

        if (!eventQueue.addEvent(event)) {
            throw new IllegalStateException("The queue is at capacity");
        }

        return new SubmissionResponse(eventId, transactionId, event.getStatus());
    }

}
