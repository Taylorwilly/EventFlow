package com.eventflow;

import java.util.UUID;

public class SubmissionResponse {
    private final UUID eventId;
    private final UUID transactionId;
    private final EventStatus status;

    public SubmissionResponse(UUID eventId, UUID transactionId, EventStatus status) {
        if (eventId == null) {
            throw new IllegalArgumentException("Event Id is required");
        }

        if (transactionId == null) {
            throw new IllegalArgumentException("Transaction Id is required");
        }

        if (status == null) {
            throw new IllegalArgumentException("Status is required");
        }

        this.eventId = eventId;
        this.transactionId = transactionId;
        this.status = status;
    }

    public UUID getEventId() {
        return eventId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public EventStatus getStatus() {
        return status;
    }
}
