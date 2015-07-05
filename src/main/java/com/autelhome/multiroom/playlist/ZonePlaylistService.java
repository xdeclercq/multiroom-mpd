package com.autelhome.multiroom.playlist;

import com.google.inject.Inject;

import java.util.Optional;

/**
 * Service to fetch playlists.
 *
 * @author xdeclercq
 */
public class ZonePlaylistService {

    private final ZonePlaylistDatabase zonePlaylistDatabase;

    /**
     * Constructor.
     *
     * @param zonePlaylistDatabase the zone playlist database
     */
    @Inject
    public ZonePlaylistService(final ZonePlaylistDatabase zonePlaylistDatabase) {
        this.zonePlaylistDatabase = zonePlaylistDatabase;
    }

    /**
     * Returns a playlist by its zone name.
     *
     * @param zoneName the name of a zone
     * @return a playlist by its zone name
     */
    public Optional<ZonePlaylistDto> getPlaylistByZoneName(final String zoneName) {
        return zonePlaylistDatabase.getByZoneName(zoneName);
    }
}
