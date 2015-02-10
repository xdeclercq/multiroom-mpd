package com.autelhome.multiroom.player;

import com.autelhome.multiroom.mpd.MPDPool;
import com.autelhome.multiroom.zone.Zone;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bff.javampd.exception.MPDPlayerException;
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
    private final MPDPool mpdPool;

    @Inject
    public PlayerCommandHandlers(final MPDPool mpdPool) {
        this.mpdPool = mpdPool;
    }

    /**
     * Issues a play command to the MPD player associated to zone {@code zone}.
     *
     * @param playCommand a play command
     */
    public void handlePlay(final PlayCommand playCommand) {
        final Zone zone = playCommand.getZone();
        final org.bff.javampd.Player mpdPlayer = mpdPool.getMPDPlayer(zone);
        try {
            mpdPlayer.play();
            LOGGER.info("zone {} - play", zone.getName());
        } catch (MPDPlayerException e) {
            LOGGER.error("Error when handling play command for zone {}", zone.getName());
        }
    }

    /**
     * Issues a stop command to the MPD player associated to zone {@code zone}.
     *
     * @param stopCommand a stop command
     */
    public void handleStop(final StopCommand stopCommand) {
        final Zone zone = stopCommand.getZone();
        final org.bff.javampd.Player mpdPlayer = mpdPool.getMPDPlayer(zone);
        try {
            mpdPlayer.stop();
            LOGGER.info("zone {} - stop", zone.getName());
        } catch (MPDPlayerException e) {
            LOGGER.error("Error when handling stop command for zone {}", zone.getName());
        }
    }

}
