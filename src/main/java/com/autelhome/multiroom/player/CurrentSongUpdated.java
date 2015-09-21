package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.AbstractEvent;
import com.google.common.base.MoreObjects;
import java.util.Objects;
import java.util.UUID;

/**
 * Event triggered when the current song of a player has been updated.
 *
 * @author xdeclercq
 */
public class CurrentSongUpdated extends AbstractEvent {

    private final CurrentSong newCurrentSong;

    /**
     * Constructor.
     *
     * @param zoneId the zone id
     * @param newCurrentSong then new current song
     */
    public CurrentSongUpdated(final UUID zoneId, final CurrentSong newCurrentSong) {
        super(zoneId);
        this.newCurrentSong = newCurrentSong;
    }

    public CurrentSong getNewCurrentSong() {
        return newCurrentSong;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final CurrentSongUpdated that = (CurrentSongUpdated) o;

        if (id != that.id) {
            return false;
        }

        return Objects.equals(newCurrentSong, that.newCurrentSong);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, newCurrentSong);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("newCurrentSong", newCurrentSong)
                .toString();
    }
}
