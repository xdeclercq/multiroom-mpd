package com.autelhome.multiroom.util;

import java.util.UUID;

/**
 * {@link RuntimeException} for MPD pool operations.
 *
 * @author xdeclercq
 */
public class AggregateInstantiationException extends RuntimeException
{

    /**
     * Constructs a new aggregate instantiation exception with the specified aggregate id and
     * cause.
     *
     * @param  aggregateId the aggregate id
     * @param  cause the cause  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public AggregateInstantiationException(final UUID aggregateId, final Throwable cause) {
        super(String.format("unable to instantiate aggregate %s", aggregateId.toString()), cause);
    }
}
