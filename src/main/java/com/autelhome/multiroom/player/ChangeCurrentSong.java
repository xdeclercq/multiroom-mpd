package com.autelhome.multiroom.player;

import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.util.AbstractCommand;
import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.UUID;

/**
 * Command to change the current song of a player.
 *
 * @author xdeclercq
 */
public class ChangeCurrentSong extends AbstractCommand {

    private final Song newCurrentSong;

    /**
     * Constructor.
     *
     * @param id the root aggregate id on which this command applies
     * @param newCurrentSong the player new current song
     * @param originalVersion the zone version on which this command applies
     */
    public ChangeCurrentSong(final UUID id, final Song newCurrentSong, final int originalVersion) {
        super(id, originalVersion);

        this.newCurrentSong = newCurrentSong;
    }

    public Song getNewCurrentSong() {
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

        final ChangeCurrentSong that = (ChangeCurrentSong) o;

        if (aggregateId != that.aggregateId) {
            return false;
        }
        if (originalVersion != that.originalVersion) {
            return false;
        }
        return Objects.equals(newCurrentSong, that.newCurrentSong);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, originalVersion, newCurrentSong);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("aggregateId", aggregateId)
                .add("originalVersion", originalVersion)
                .add("newCurrentSong", newCurrentSong)
                .toString();
    }
}
