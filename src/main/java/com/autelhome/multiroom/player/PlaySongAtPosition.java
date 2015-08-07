package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.AbstractCommand;
import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.UUID;

/**
 * Command to play a selected song with the player associated to a zone.
 *
 * @author xdeclercq
 */
public class PlaySongAtPosition extends AbstractCommand {

    private final int position;

    /**
     * Constructor.
     *
     * @param id the player id
     * @param position the position in the playlist of the song to play
     * @param originalVersion the zone version on which this command applies
     */
    public PlaySongAtPosition(final UUID id, final int position, final int originalVersion) {
        super(id, originalVersion);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final PlaySongAtPosition that = (PlaySongAtPosition) o;

        if (aggregateId != that.aggregateId) {
            return false;
        }
        if (originalVersion != that.originalVersion) {
            return false;
        }
        return position == that.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, originalVersion, position);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("aggregateId", aggregateId)
                .add("originalVersion", originalVersion)
                .add("position", position)
                .toString();
    }
}
