package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.AbstractEvent;
import com.google.common.base.MoreObjects;
import java.util.Objects;
import java.util.UUID;

/**
 * Event triggered when the song of a player has been selected to play.
 *
 * @author xdeclercq
 */
public class SongAtPositionPlayed extends AbstractEvent {

    private final CurrentSong currentSong;

    /**
     * Constructor.
     *
     * @param zoneId the zone id
     * @param currentSong the current song
     */
    public SongAtPositionPlayed(final UUID zoneId, final CurrentSong currentSong) {
        super(zoneId);
        this.currentSong = currentSong;
    }

    public CurrentSong getCurrentSong() {
        return currentSong;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final SongAtPositionPlayed that = (SongAtPositionPlayed) o;

        if (id != that.id) {
            return false;
        }

        return Objects.equals(currentSong, that.currentSong);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currentSong);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("currentSong", currentSong)
                .toString();
    }
}
