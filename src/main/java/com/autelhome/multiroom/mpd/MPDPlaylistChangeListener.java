package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.playlist.ChangeZonePlaylist;
import com.autelhome.multiroom.playlist.ZonePlaylist;
import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneRepository;
import org.bff.javampd.events.PlayerBasicChangeListener;
import org.bff.javampd.events.PlaylistBasicChangeEvent;
import org.bff.javampd.events.PlaylistBasicChangeListener;
import org.bff.javampd.monitor.MPDPlaylistMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author xdeclercq
 */
public class MPDPlaylistChangeListener implements PlaylistBasicChangeListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerBasicChangeListener.class);
    private final UUID zoneId;
    private final EventBus eventBus;
    private final ZoneRepository zoneRepository;
    private final MPDGateway mpdGateway;


    /**
     * Constructor.
     *
     * @param zoneId a zone id
     * @param eventBus the event bus
     * @param zoneRepository the zone repository
     * @param mpdGateway mpdGateway the gateway to the MPD instance for this zone
     */
    public MPDPlaylistChangeListener(final UUID zoneId, final EventBus eventBus, final ZoneRepository zoneRepository, final MPDGateway mpdGateway) {
        this.zoneId = zoneId;
        this.eventBus = eventBus;
        this.zoneRepository = zoneRepository;
        this.mpdGateway = mpdGateway;

    }

    @Override
    public void playlistBasicChange(final PlaylistBasicChangeEvent event) {
        final MPDPlaylistMonitor source = (MPDPlaylistMonitor) event.getSource();
        LOGGER.info("[{}] Received MPD playlist basic change event '{}': {}", zoneId, event.getEvent(), source.getSongId());

        final Zone zone = zoneRepository.getById(zoneId);

        final ZonePlaylist newPlaylist = mpdGateway.getZonePlaylist(zone.getMpdInstancePortNumber());
        final ChangeZonePlaylist changeZonePlaylist = new ChangeZonePlaylist(zone.getId(), newPlaylist, zone.getVersion());

        eventBus.send(changeZonePlaylist);
    }


}
