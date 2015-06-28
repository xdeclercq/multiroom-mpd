package com.autelhome.multiroom.socket;

import javax.websocket.Session;

/**
 * {@link RuntimeException} for broadcasting issues.
 *
 * @author xdeclercq
 */
public class BroadcastException extends RuntimeException
{

    /**
     * Constructs a new broadcast exception for the session.
     *
     * @param session a session
     * @param cause the cause
     */
    public BroadcastException(final Session session, final Throwable cause) {
        super(String.format("Unable to broadcast message to sesssion %s", session.getId()), cause);
    }

}
