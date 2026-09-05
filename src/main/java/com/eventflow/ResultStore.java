package com.eventflow;

import java.util.UUID;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ResultStore {
    private final Map<UUID, EventResult> results = new HashMap<>();

    public void storeResult(UUID eventId, EventResult result) {
        if (eventId == null) {
            throw new IllegalArgumentException("Event id is required");
        }

        if (result == null) {
            throw new IllegalArgumentException("Result is required");
        }

        if (results.containsKey(eventId)) {
            throw new IllegalArgumentException("A result with this id already exists");
        }
        results.put(eventId, result);
    }

    public Optional<EventResult> findResult(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is required");
        }

        return Optional.ofNullable(results.get(id));
    }
}
