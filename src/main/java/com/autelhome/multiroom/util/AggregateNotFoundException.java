package com.autelhome.multiroom.util;

import java.util.UUID;

/**
 * {@link RuntimeException} for aggregates not found.
 *
 * @author xdeclercq
 */
public class AggregateNotFoundException extends RuntimeException
{

    /**
     * Constructs a new aggregate not found exception for the specified aggregate id.
     *
     * @param aggregateId an aggregate id
     */
    public AggregateNotFoundException(final UUID aggregateId) {
        super(String.format("aggregate %s has not been found", aggregateId.toString()));
    }

}
