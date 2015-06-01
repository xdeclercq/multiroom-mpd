package com.autelhome.multiroom.util;

import java.util.UUID;

/**
 * A command.
 *
 * @author xdeclercq
 */
public interface Command extends Message {

    /**
     * Returns the aggregate id on which this command applies.
     *
     * @return the aggregate id on which this command applies.
     */
    UUID getAggregateId();

    /**
     * Returns the original version of the aggregate on which
     * this command applies or -1 if the aggregate does not exist yet.
     *
     * @return the original version of the aggregate on which
     * this command applies or -1 if the aggregate does not exist yet
     */
    int getOriginalVersion();
}
