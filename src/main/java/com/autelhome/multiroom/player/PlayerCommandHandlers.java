package com.autelhome.multiroom.player;

import com.autelhome.multiroom.mpd.MPDGateway;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Player related command handlers.
 *
 * @author xdeclercq
 */
@Singleton
public class PlayerCommandHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerCommandHandlers.class);
    private final MPDGateway mpdGateway;
    private final ZoneRepository zoneRepository;

    @Inject
    public PlayerCommandHandlers(final MPDGateway mpdGateway, final ZoneRepository zoneRepository) {
        this.mpdGateway = mpdGateway;
        this.zoneRepository = zoneRepository;
    }

    /**
     * Issues a play command to the appropriate MPD instance.
     *
     * @param play a play command
     */
    public void handlePlay(final Play play) {
        final Zone zone = zoneRepository.getById(play.getAggregateId());
        mpdGateway.play(zone.getMpdInstancePortNumber());
        zone.play();
        final String zoneName = zone.getName();
        LOGGER.info("zone {} - handle play", zoneName);

        zoneRepository.save(zone, play.getOriginalVersion());
    }

    /**
     * Issues a pause command to the appropriate MPD instance.
     *
     * @param pause a pause command
     */
    public void handlePause(final Pause pause) {
        final Zone zone = zoneRepository.getById(pause.getAggregateId());
        mpdGateway.pause(zone.getMpdInstancePortNumber());
        zone.pause();
        final String zoneName = zone.getName();
        LOGGER.info("zone {} - handle pause", zoneName);

        zoneRepository.save(zone, pause.getOriginalVersion());
    }

    /**
     * Issues a stop command to the appropriate MPD instance.
     *
     * @param stop a stop command
     */
    public void handleStop(final Stop stop) {
        final Zone zone = zoneRepository.getById(stop.getAggregateId());
        mpdGateway.stop(zone.getMpdInstancePortNumber());
        zone.stop();
        final String zoneName = zone.getName();
        LOGGER.info("zone {} - handle pause", zoneName);

        zoneRepository.save(zone, stop.getOriginalVersion());
    }

    /**
     * Changes the palyer status.
     *
     * @param changePlayerStatus a change player status command.
     */
    public void handleChangePlayerStatus(final ChangePlayerStatus changePlayerStatus) {
        final Zone zone = zoneRepository.getById(changePlayerStatus.getAggregateId());
        LOGGER.info("[{}] - changing status to {}", zone.getName(), changePlayerStatus.getNewStatus());
        zone.changePlayerStatus(changePlayerStatus.getNewStatus());
        zoneRepository.save(zone, changePlayerStatus.getOriginalVersion());
    }

}
