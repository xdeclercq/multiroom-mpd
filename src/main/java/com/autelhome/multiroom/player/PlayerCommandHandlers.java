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

    /**
     * Constructor.
     *
     * @param mpdGateway an {@link MPDGateway} instance
     * @param zoneRepository a {link ZoneRepository} instance
     */
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
        zone.play();
        mpdGateway.play(zone.getMpdInstancePortNumber());
        final String zoneName = zone.getName();
        LOGGER.info("zone {} - handle play", zoneName);

        zoneRepository.save(zone, play.getOriginalVersion());
    }

    /**
     * Issues a play song command to the appropriate MPD instance.
     *
     * @param playSongAtPosition a play song command
     */
    public void handlePlaySong(final PlaySongAtPosition playSongAtPosition) {
        final Zone zone = zoneRepository.getById(playSongAtPosition.getAggregateId());
        final int position = playSongAtPosition.getPosition();
        zone.playSongAtPosition(position);
        mpdGateway.playSongAtPosition(zone.getMpdInstancePortNumber(), position);
        final String zoneName = zone.getName();
        LOGGER.info("zone {} - handle play song (position {})", zoneName, position);

        zoneRepository.save(zone, playSongAtPosition.getOriginalVersion());
    }

    /**
     * Issues a pause command to the appropriate MPD instance.
     *
     * @param pause a pause command
     */
    public void handlePause(final Pause pause) {
        final Zone zone = zoneRepository.getById(pause.getAggregateId());
        zone.pause();
        mpdGateway.pause(zone.getMpdInstancePortNumber());
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
        zone.stop();
        mpdGateway.stop(zone.getMpdInstancePortNumber());
        final String zoneName = zone.getName();
        LOGGER.info("zone {} - handle pause", zoneName);

        zoneRepository.save(zone, stop.getOriginalVersion());
    }

    /**
     * Changes the player current song.
     *
     * @param changeCurrentSong a change player status command.
     */
    public void handleChangeCurrentSong(final ChangeCurrentSong changeCurrentSong) {
        final Zone zone = zoneRepository.getById(changeCurrentSong.getAggregateId());
        LOGGER.info("[{}] - changing current song to {}", zone.getName(), changeCurrentSong.getNewCurrentSong());
        zone.changeCurrentSong(changeCurrentSong.getNewCurrentSong());
        zoneRepository.save(zone, changeCurrentSong.getOriginalVersion());
    }

}
