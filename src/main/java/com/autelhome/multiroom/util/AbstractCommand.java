package com.autelhome.multiroom.util;

import java.util.Objects;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AbstractCommand that = (AbstractCommand) o;

        if (originalVersion != that.originalVersion) {
            return false;
        }

        return Objects.equals(aggregateId, that.aggregateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, originalVersion);
    }
}
