package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.util.InstanceNotFoundException;
import com.autelhome.multiroom.zone.ZoneCreated;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Optional;
import java.util.UUID;

/**
 * View of zone playlists.
 *
 * @author xdeclercq
 */
@Singleton
public class ZonePlaylistsView {

    private final ZonePlaylistDatabase zonePlaylistDatabase;

    /**
     * Constructor.
     *
     * @param zonePlaylistDatabase the zone playlist database
     */
    @Inject
    public ZonePlaylistsView(final ZonePlaylistDatabase zonePlaylistDatabase) {
        this.zonePlaylistDatabase = zonePlaylistDatabase;
    }

    /**
     * Updates database with the created player.
     *
     * @param zoneCreated a zone created event
     */
    public void handleCreated(final ZoneCreated zoneCreated) {
        zonePlaylistDatabase.add(new ZonePlaylistDto(zoneCreated.getId(), zoneCreated.getName(), zoneCreated.getPlaylist()));
    }

    /**
     * Updates database with a zone playlist updated event.
     *
     * @param zonePlaylistUpdated a player status updated event
     */
    public void handleZonePlaylistUpdated(final ZonePlaylistUpdated zonePlaylistUpdated) {

        final UUID zoneId = zonePlaylistUpdated.getId();

        final Optional<ZonePlaylistDto> playlistOptional = zonePlaylistDatabase.getByZoneId(zoneId);
        if (!playlistOptional.isPresent()) {
            throw new InstanceNotFoundException("The zone '" + zoneId + "' is not present in the database");
        }
        final ZonePlaylistDto playlist = playlistOptional.get();
        final String zoneName = playlist.getZoneName();
        final ZonePlaylist newPlaylist = zonePlaylistUpdated.getNewPlaylist();
        final ZonePlaylistDto newPlaylistDto = new ZonePlaylistDto(zoneId, zoneName, newPlaylist);
        zonePlaylistDatabase.update(newPlaylistDto);
    }
}
