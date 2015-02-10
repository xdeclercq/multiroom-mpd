package com.autelhome.multiroom.mpd;

/**
 * {@link RuntimeException} for MPD pool operations.
 *
 * @author xdeclercq
 */
public class MPDPoolException extends RuntimeException
{

    /**
     * Constructs a new MPD pool exception with the specified detail message and
     * cause.
     *
     * @param  message the detail message
     * @param  cause the cause  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public MPDPoolException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
