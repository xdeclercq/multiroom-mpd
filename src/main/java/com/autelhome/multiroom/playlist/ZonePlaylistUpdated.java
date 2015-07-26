package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.util.AbstractEvent;
import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.UUID;

/**
 * Event triggered when the status of a zone playlist has been updated.
 *
 * @author xdeclercq
 */
public class ZonePlaylistUpdated extends AbstractEvent {

    private final ZonePlaylist newPlaylist;

    /**
     * Constructor.
     *
     * @param zoneId the zone id
     * @param newPlaylist the new playlist
     */
    public ZonePlaylistUpdated(final UUID zoneId, final ZonePlaylist newPlaylist) {
        super(zoneId);
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

        final ZonePlaylistUpdated that = (ZonePlaylistUpdated) o;

        if (id != that.id) {
            return false;
        }
        if (!Objects.equals(newPlaylist, that.newPlaylist)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, newPlaylist);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("newPlayerStatus", newPlaylist)
                .toString();
    }
}
