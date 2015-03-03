package com.autelhome.multiroom.util;

/**
 * Event Bus to send commands to observers.
 *
 * @author xdeclercq
 */
public interface EventBus extends CommandSender, EventPublisher {

    /**
     * Registers a handler.
     *
     * @param handler a handler
     * @param messageClass the message class
     */
    <M extends Message> void register(final MessageHandler<M> handler, final Class<M> messageClass);

}
