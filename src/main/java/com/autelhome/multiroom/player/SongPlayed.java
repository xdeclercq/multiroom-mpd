package com.autelhome.multiroom.player;

import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.util.AbstractEvent;
import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.UUID;

/**
 * Event triggered when the song of a player has been selected to play.
 *
 * @author xdeclercq
 */
public class SongPlayed extends AbstractEvent {

    private final Song song;

    /**
     * Constructor.
     *
     * @param zoneId the zone id
     * @param song then new current song
     */
    public SongPlayed(final UUID zoneId, final Song song) {
        super(zoneId);
        this.song = song;
    }

    public Song getSong() {
        return song;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final SongPlayed that = (SongPlayed) o;

        if (id != that.id) {
            return false;
        }

        return Objects.equals(song, that.song);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, song);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("song", song)
                .toString();
    }
}
