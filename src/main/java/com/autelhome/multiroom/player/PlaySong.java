package com.autelhome.multiroom.player;

import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.util.AbstractCommand;
import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.UUID;

/**
 * Command to play a selected song with the player associated to a zone.
 *
 * @author xdeclercq
 */
public class PlaySong extends AbstractCommand {

    private final Song song;

    /**
     * Constructor.
     *
     * @param id the player id
     * @param song the song to play
     * @param originalVersion the zone version on which this command applies
     */
    public PlaySong(final UUID id, final Song song, final int originalVersion) {
        super(id, originalVersion);
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

        final PlaySong that = (PlaySong) o;

        if (aggregateId != that.aggregateId) {
            return false;
        }
        if (originalVersion != that.originalVersion) {
            return false;
        }
        return Objects.equals(song, that.song);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, originalVersion, song);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("aggregateId", aggregateId)
                .add("originalVersion", originalVersion)
                .add("song", song)
                .toString();
    }
}
