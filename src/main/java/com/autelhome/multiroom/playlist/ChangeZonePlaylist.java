package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.util.AbstractCommand;
import com.google.common.base.MoreObjects;
import java.util.Objects;
import java.util.UUID;

/**
 * Command to change a zone playlist.
 *
 * @author xdeclercq
 */
public class ChangeZonePlaylist extends AbstractCommand {

    private final ZonePlaylist newPlaylist;

    /**
     * Constructor.
     *
     * @param zoneId the zone id on which this command applies
     * @param newPlaylist the new playlist
     * @param originalVersion the zone version on which this command applies
     */
    public ChangeZonePlaylist(final UUID zoneId, final ZonePlaylist newPlaylist, final int originalVersion) {
        super(zoneId, originalVersion);

        this.newPlaylist = newPlaylist;
    }

    public ZonePlaylist getNewPlaylist() {
        return newPlaylist;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ChangeZonePlaylist that = (ChangeZonePlaylist) o;

        if (aggregateId != that.aggregateId) {
            return false;
        }
        if (originalVersion != that.originalVersion) {
            return false;
        }

        return Objects.equals(newPlaylist, that.newPlaylist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, originalVersion, newPlaylist);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("aggregateId", aggregateId)
                .add("originalVersion", originalVersion)
                .add("newPlaylist", newPlaylist)
                .toString();
    }
}
