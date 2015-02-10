package com.autelhome.multiroom.util;

/**
 * Handler of a command.
 *
 * @author xdeclercq
 *
 * @param <C> the command type that this command handler handles
 */
public interface CommandHandler<C extends Command> {

    void handle(C command);
}
