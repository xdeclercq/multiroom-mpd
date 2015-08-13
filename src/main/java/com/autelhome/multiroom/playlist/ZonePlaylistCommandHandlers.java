package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Zone playlist related command handlers.
 *
 * @author xdeclercq
 */
@Singleton
public class ZonePlaylistCommandHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZonePlaylistCommandHandlers.class);
    private final ZoneRepository zoneRepository;

    @Inject
    public ZonePlaylistCommandHandlers(final ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }


    /**
     * Changes the zone playlist.
     *
     * @param changeZonePlaylist a change zone playlist command.
     */
    public void handleChangeZonePlaylist(final ChangeZonePlaylist changeZonePlaylist) {
        final Zone zone = zoneRepository.getById(changeZonePlaylist.getAggregateId());
        final ZonePlaylist newPlaylist = changeZonePlaylist.getNewPlaylist();
        final Collection<PlaylistSong> newPlaylistSongs = newPlaylist.getPlaylistSongs();
        LOGGER.info("[{}] - changing playlist - {} songs", zone.getName(), newPlaylistSongs.size());
        zone.changePlaylist(newPlaylist);
        zoneRepository.save(zone, changeZonePlaylist.getOriginalVersion());
    }

}
