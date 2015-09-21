package com.autelhome.multiroom.util;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import java.util.UUID;

/**
 * Abstract event.
 *
 * @author xdeclercq
 */
public abstract class AbstractEvent implements Event {

    private int version;
    protected final UUID id;

    /**
     * Constructor.
     *
     * @param aggregateId the aggregate id
     */
    protected AbstractEvent(final UUID aggregateId) {
        this.id = aggregateId;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(final int version) {
        this.version = version;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AbstractEvent that = (AbstractEvent) o;

        if (id != that.id) {
            return false;
        }

        if (version != that.version) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("version", version)
                .toString();
    }
}
