package com.eventflow;

import java.lang.IllegalStateException;
import java.util.UUID;
import java.time.Instant;

public class Event {
    private final UUID id;
    private final Instant createdAt;
    private final EventPayload payload;
    private final EventType type;
    private EventStatus status;

    public Event(
            UUID id,
            EventPayload payload,
            EventType type) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid Id");
        }
        if (payload == null) {
            throw new IllegalArgumentException("Invalid payload");
        }
        if (type == null) {
            throw new IllegalArgumentException("Invalid type");
        }

        this.id = id;
        this.createdAt = Instant.now();
        this.payload = payload;
        this.type = type;
        this.status = EventStatus.PENDING;

    }

    public UUID getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public EventPayload getPayload() {
        return payload;
    }

    public EventType getType() {
        return type;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void startProcessing() {
        if (status == EventStatus.PENDING) {
            this.status = EventStatus.PROCESSING;
        } else {
            throw new IllegalStateException("The state must be pending not: " + status);
        }
    }

    public void complete() {
        if (status == EventStatus.PROCESSING) {
            this.status = EventStatus.COMPLETED;
        } else {
            throw new IllegalStateException("The status must be processing not:" + status);
        }
    }

    public void fail() {
        if (status == EventStatus.PROCESSING) {
            this.status = EventStatus.FAILED;
        } else {
            throw new IllegalStateException("The status must be processing not:" + status);
        }
    }
}