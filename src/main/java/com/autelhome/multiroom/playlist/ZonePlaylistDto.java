package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.song.Song;
import com.google.common.base.MoreObjects;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a zone playlist data transfer object.
 *
 * A playlist contains the list of playlist being played in a zone.
 *
 * @author xdeclercq
 */
public class ZonePlaylistDto
{

    private final UUID zoneId;
    private final String zoneName;
    private final ZonePlaylist playlist;

    /**
     * Constructor.
     *
     * @param zoneId the id of the zone
     * @param zoneName the name of the zone
     * @param playlist the playlist
     */
    public ZonePlaylistDto(final UUID zoneId, final String zoneName, final ZonePlaylist playlist)
    {
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.playlist = playlist;
    }

    public String getZoneName()
    {
        return zoneName;
    }

    public Collection<Song> getSongs() {
        return playlist.getSongs();
    }

    public UUID getZoneId() {
        return zoneId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(zoneId, zoneName, playlist);
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ZonePlaylistDto playerDto = (ZonePlaylistDto) o;

        if (zoneId != playerDto.zoneId) {
            return false;
        }

        if (!Objects.equals(playlist, playerDto.playlist)) {
            return false;
        }
        return zoneName.equals(playerDto.zoneName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("zoneId", zoneId)
                .add("zoneName", zoneName)
                .add("playlist", playlist)
                .toString();
    }
}
