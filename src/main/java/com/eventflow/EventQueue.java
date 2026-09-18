package com.eventflow;

import java.util.concurrent.ArrayBlockingQueue;

public class EventQueue {
    private final ArrayBlockingQueue<Event> events = new ArrayBlockingQueue<>(10, false);

    public boolean addEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event must not be null");
        }
        return events.offer(event);
    }

    public Event getNextEvent() {
        return events.poll();
    }

    public Event peekNextEvent() {
        return events.peek();
    }

    public boolean hasEvents() {
        return !events.isEmpty();
    }

}
