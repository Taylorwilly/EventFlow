package com.eventflow;

public interface EventProcessor<P extends EventPayload, R> {
    R process(P payload);

}
