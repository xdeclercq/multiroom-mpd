package com.autelhome.multiroom.util;

/**
 * Event Bus to send commands to observers.
 *
 * @author xdeclercq
 */
public interface EventBus {

    /**
     * Registers a handler.
     *
     * @param handler a handler
     * @param clazz the command class
     */
    <C extends Command> void register(final CommandHandler<C> handler, final Class<C> clazz);

    /**
     * Sends a command to observers.
     *
     * @param command a command
     */
    void send(final Command command);
}
