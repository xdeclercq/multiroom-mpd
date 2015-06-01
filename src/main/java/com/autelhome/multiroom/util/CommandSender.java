package com.autelhome.multiroom.util;

/**
 * Sender of commands.
 *
 * @author xdeclercq
 */
public interface CommandSender {

    /**
     * Sends the command to the registered handlers for this command type.
     *
     * @param command a command
     */
    void send(Command command);
}
