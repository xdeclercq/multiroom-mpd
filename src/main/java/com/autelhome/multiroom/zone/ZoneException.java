package com.autelhome.multiroom.zone;

/**
 * {@link RuntimeException} for zone operations.
 *
 * @author xavier
 */
public class ZoneException extends RuntimeException
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
    public ZoneException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
