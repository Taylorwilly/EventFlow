package com.eventflow;

import java.util.ArrayDeque;
import java.util.Queue;

public class EventQueue {
    private final Queue<Event> events = new ArrayDeque<>();

    public void addEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event must not be null");
        }
        events.offer(event);
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
