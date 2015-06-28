package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.AbstractEvent;
import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.UUID;

/**
 * Event triggered when the status of a player has been updated.
 *
 * @author xdeclercq
 */
public class PlayerStatusUpdated extends AbstractEvent {

    private final PlayerStatus newPlayerStatus;

    /**
     * Constructor.
     *
     * @param zoneId the zone id
     * @param newPlayerStatus then new player status
     */
    public PlayerStatusUpdated(final UUID zoneId, final PlayerStatus newPlayerStatus) {
        super(zoneId);
        this.newPlayerStatus = newPlayerStatus;
    }

    public PlayerStatus getNewPlayerStatus() {
        return newPlayerStatus;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final PlayerStatusUpdated that = (PlayerStatusUpdated) o;

        if (id != that.id) {
            return false;
        }
        if (newPlayerStatus != that.newPlayerStatus) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, newPlayerStatus);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("newPlayerStatus", newPlayerStatus)
                .toString();
    }
}
