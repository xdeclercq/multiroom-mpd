package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.player.Pause;
import com.autelhome.multiroom.player.Play;
import com.autelhome.multiroom.player.Stop;
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

        final Zone zone = zoneRepository.getById(zoneId);

        switch (event.getStatus()) {
            case PLAYER_STARTED:
            case PLAYER_UNPAUSED:
                eventBus.send(new Play(zoneId, zone.getVersion()));
                break;
            case PLAYER_PAUSED:
                eventBus.send(new Pause(zoneId, zone.getVersion()));
                break;
            case PLAYER_STOPPED:
                eventBus.send(new Stop(zoneId, zone.getVersion()));
                break;
            default:
                // do nothing
        }
    }


}
