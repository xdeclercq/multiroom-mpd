package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.player.ChangePlayerStatus;
import com.autelhome.multiroom.player.PlayerStatus;
import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneRepository;
import org.bff.javampd.events.PlayerBasicChangeEvent;
import org.bff.javampd.events.PlayerBasicChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author xdeclercq
 */
public class MPDPlayerChangeListener implements PlayerBasicChangeListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerBasicChangeListener.class);
    private final UUID zoneId;
    private final EventBus eventBus;
    private final ZoneRepository zoneRepository;

    /**
     * Constructor.
     *
     * @param zoneId a zone id
     * @param zoneRepository the zone repository
     */
    public MPDPlayerChangeListener(final UUID zoneId, final EventBus eventBus, final ZoneRepository zoneRepository) {
        this.zoneId = zoneId;
        this.eventBus = eventBus;
        this.zoneRepository = zoneRepository;

    }

    @Override
    public void playerBasicChange(final PlayerBasicChangeEvent event) {
        LOGGER.info("[{}] Received MPD player basic change event '{}'", zoneId, event.getStatus());

        final PlayerStatus playerStatus = PlayerStatus.fromMPDChangeEventStatus(event.getStatus());

        final Zone zone = zoneRepository.getById(zoneId);
        eventBus.send(new ChangePlayerStatus(zoneId, playerStatus, zone.getVersion()));
    }


}
