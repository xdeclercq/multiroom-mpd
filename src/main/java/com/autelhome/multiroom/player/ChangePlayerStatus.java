package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.AbstractCommand;
import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.UUID;

/**
 * Command to change the status of a player.
 *
 * @author xdeclercq
 */
public class ChangePlayerStatus extends AbstractCommand {

    private final PlayerStatus newStatus;

    /**
     * Constructor.
     *
     * @param id the root aggregate id on which this command applies
     * @param newStatus the player new status
     * @param originalVersion the zone version on which this command applies
     */
    public ChangePlayerStatus(final UUID id, final PlayerStatus newStatus, final int originalVersion) {
        super(id, originalVersion);

        this.newStatus = newStatus;
    }

    public PlayerStatus getNewStatus() {
        return newStatus;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ChangePlayerStatus that = (ChangePlayerStatus) o;

        if (aggregateId != that.aggregateId) {
            return false;
        }
        if (originalVersion != that.originalVersion) {
            return false;
        }
        if (newStatus != that.newStatus) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, originalVersion, newStatus);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("aggregateId", aggregateId)
                .add("originalVersion", originalVersion)
                .add("newStatus", newStatus)
                .toString();
    }
}
