package com.autelhome.multiroom.player;

/**
 * {@link RuntimeException} for player operations.
 *
 * @author xdeclercq
 */
public class PlayerException extends RuntimeException
{

    /**
     * Constructs a new player exception with the specified detail message and
     * cause.
     *
     * @param  message the detail message
     * @param  cause the cause  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public PlayerException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
