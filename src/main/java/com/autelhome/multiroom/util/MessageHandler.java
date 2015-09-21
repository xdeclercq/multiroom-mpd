package com.autelhome.multiroom.util;

/**
 * Handler of a message (command or event).
 *
 * @param <M> the message type that this handler handles
 * @author xdeclercq
 */
public interface MessageHandler<M extends Message> {

    /**
     * Handles a message.
     *
     * @param message a message
     */
    void handle(M message);
}
