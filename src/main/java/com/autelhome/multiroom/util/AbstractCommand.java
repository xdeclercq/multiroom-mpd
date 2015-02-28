package com.autelhome.multiroom.util;

import java.util.UUID;

/**
 * Abstract command.
 *
 * @author xdeclercq
 */
public abstract class AbstractCommand implements Command {

    protected final UUID aggregateId;
    protected final int originalVersion;

    /**
     * Constructor.
     *
     * @param aggregateId the aggregate id on which this command applies
     * @param originalVersion the original version of the aggregate on which
     * this command applies
     */
    protected AbstractCommand(final UUID aggregateId, final int originalVersion) {
        this.aggregateId = aggregateId;
        this.originalVersion = originalVersion;
    }

    @Override
    public UUID getAggregateId() {
        return aggregateId;
    }

    @Override
    public int getOriginalVersion() {
        return originalVersion;
    }
}
