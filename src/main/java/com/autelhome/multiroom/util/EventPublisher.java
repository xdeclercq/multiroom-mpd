package com.autelhome.multiroom.util;

/**
 * Publisher of events.
 *
 * @author xdeclercq
 */
public interface EventPublisher {

    /**
     * Publishes the event to the registered handlers for this event type.
     *
     * @param event an event.
     */
    void publish(Event event);
}
