package com.autelhome.multiroom.mpd;

/**
 * {@link RuntimeException} for MPD operations.
 *
 * @author xdeclercq
 */
public class MPDException extends RuntimeException
{

    /**
     * Constructs a new MPD exception with the specified detail message and
     * cause.
     *
     * @param  message the detail message
     * @param  cause the cause  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public MPDException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
