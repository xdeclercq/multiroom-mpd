package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.util.InstanceAlreadyPresentException;
import com.autelhome.multiroom.util.InstanceNotFoundException;
import com.google.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Database to store players.
 *
 * @author xdeclercq
 */
@Singleton
public class ZonePlaylistDatabase {

    private final Map<String, ZonePlaylistDto> playlistsByZoneName = new HashMap<>();
    private final Map<UUID, ZonePlaylistDto> playlistsByZoneId = new HashMap<>();

    /**
     * Adds a zone playlist to the database.
     *
     * @param playlistDto a zone playlist
     */
    public void add(final ZonePlaylistDto playlistDto) {
        if (playlistsByZoneId.containsKey(playlistDto.getZoneId()) || playlistsByZoneName.containsKey(playlistDto.getZoneName())) {
            throw new InstanceAlreadyPresentException("The player of zone '" + playlistDto.getZoneId() + "' is already present in the database");
        }

        playlistsByZoneName.put(playlistDto.getZoneName(), playlistDto);
        playlistsByZoneId.put(playlistDto.getZoneId(), playlistDto);
    }

    /**
     * Updates a playlist from the database.
     *
     * @param playlistDto a zone playlist
     */
    public void update(final ZonePlaylistDto playlistDto) {
        final UUID zoneId = playlistDto.getZoneId();
        final ZonePlaylistDto currentPlaylist = playlistsByZoneId.get(zoneId);
        if (currentPlaylist == null) {
            throw new InstanceNotFoundException("The playlist of zone '" + zoneId + "' is not present in the database");
        }

        playlistsByZoneName.remove(currentPlaylist.getZoneName());

        playlistsByZoneName.put(playlistDto.getZoneName(), playlistDto);
        playlistsByZoneId.put(zoneId, playlistDto);
    }

    /**
     * Returns a playlist by its zone name.
     *
     * @param zoneName the name of a zone
     * @return a playlist by its zone name
     */
    public Optional<ZonePlaylistDto> getByZoneName(final String zoneName) {
        return Optional.ofNullable(playlistsByZoneName.get(zoneName));
    }

    /**
     * Returns a player by its zone id.
     *
     * @param zoneId a zone id
     * @return the player related to the zone of id {@code zoneId}
     */
    public Optional<ZonePlaylistDto> getByZoneId(final UUID zoneId) {
        return Optional.ofNullable(playlistsByZoneId.get(zoneId));
    }
}
