package com.eventflow;

import java.util.Map;

public class Worker {
    private final EventQueue eventQueue;
    private final ResultStore resultStore;
    private final Map<EventType, ProcessorRegistration<?, ?>> processors;

    public Worker(EventQueue eventQueue,
            ResultStore resultStore,
            Map<EventType, ProcessorRegistration<?, ?>> processors) {
        if (eventQueue == null) {
            throw new IllegalArgumentException("Event is required");
        }

        if (resultStore == null) {
            throw new IllegalArgumentException("Result is required");
        }

        if (processors == null) {
            throw new IllegalArgumentException("Processor registry is required");
        }

        if (processors.isEmpty()) {
            throw new IllegalArgumentException("Processor registry should not be empty");
        }

        this.eventQueue = eventQueue;
        this.resultStore = resultStore;
        this.processors = processors;
    }

    public void processNextEvent() {
        Event event = eventQueue.getNextEvent();

        if (event == null) {
            return;
        }

        EventType type = event.getType();
        ProcessorRegistration<?, ?> registration = processors.get(type);

        if (registration == null) {
            throw new IllegalStateException("Registration is required");
        }

        event.startProcessing();

        try {
            EventPayload payload = event.getPayload();
            EventResult result = registration.invokeProcessor(payload);
            resultStore.storeResult(event.getId(), result);

            event.complete();
        } catch (Exception e) {
            event.fail();
            throw new IllegalStateException(e);
        }
    }
}
