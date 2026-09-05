package com.eventflow;

public class ProcessorRegistration<P extends EventPayload, R extends EventResult> {
    private final Class<P> payloadClass;
    private final EventProcessor<P, R> processor;

    public ProcessorRegistration(Class<P> payloadClass, EventProcessor<P, R> processor) {
        if (payloadClass == null) {
            throw new IllegalArgumentException("Payload is required");
        }

        if (processor == null) {
            throw new IllegalArgumentException("Processor is required");
        }

        this.payloadClass = payloadClass;
        this.processor = processor;
    }

    public Class<P> getPayloadClass() {
        return payloadClass;
    }

    public EventProcessor<P, R> getProcessor() {
        return processor;
    }

    public R invokeProcessor(EventPayload payload) {
        if (payload == null) {
            throw new IllegalArgumentException("Payload is required");
        }
        if (!payloadClass.isInstance(payload)) {
            throw new IllegalStateException("Payload type does not match processor registration.");
        }
        P typedPayload = payloadClass.cast(payload);
        return processor.process(typedPayload);
    }
}
