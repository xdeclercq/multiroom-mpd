package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.player.PlayerStatus;
import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneRepository;
import com.google.inject.Inject;
import org.bff.javampd.MPD;
import org.bff.javampd.Player;
import org.bff.javampd.StandAloneMonitor;
import org.bff.javampd.exception.MPDPlayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gateway to MPD instances.
 *
 * @author xdeclercq
 */
public class MPDGateway
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MPDGateway.class);

    private final MPDProvider mpdProvider;
    private final EventBus eventBus;
    private final ZoneRepository zoneRepository;

    /**
     * Constructor.
     *
     * @param mpdProvider the MPD provider
     * @param eventBus the event bus
     * @param zoneRepository the zone repository
     */
    @Inject
    public MPDGateway(final MPDProvider mpdProvider, final EventBus eventBus, final ZoneRepository zoneRepository)
    {
        this.mpdProvider = mpdProvider;
        this.eventBus = eventBus;
        this.zoneRepository = zoneRepository;
    }

    /**
     * Returns the player current status
     *
     * @param mpdInstancePortNumber the MPD instance port number
     * @return the player current status
     */
    public PlayerStatus getPlayerStatus(final int mpdInstancePortNumber) {
        final Player mpdPlayer = getMPDPlayer(mpdInstancePortNumber);
        try {
            return PlayerStatus.fromMPDPlayerStatus(mpdPlayer.getStatus());
        } catch (MPDPlayerException e) {
            LOGGER.error("Error when looking of MPD player with port {}", mpdInstancePortNumber);
        }
        return PlayerStatus.UNKNOWN;
    }

    /**
     * Issues a play command to the appropriate MPD instance.
     *
     * @param mpdInstancePortNumber the MPD instance port number
     */
    public void play(final int mpdInstancePortNumber) {
        final Player mpdPlayer = getMPDPlayer(mpdInstancePortNumber);
        try {
            mpdPlayer.play();
        } catch (MPDPlayerException e) {
            LOGGER.error("Error when handling play command for MPD instance of port {}", mpdInstancePortNumber);
        }
    }

    /**
     * Issues a pause command to the appropriate MPD instance.
     *
     * @param mpdInstancePortNumber the MPD instance port number
     */
    public void pause(final int mpdInstancePortNumber) {
        final Player mpdPlayer = getMPDPlayer(mpdInstancePortNumber);
        try {
            mpdPlayer.pause();
        } catch (MPDPlayerException e) {
            LOGGER.error("Error when handling pause command for MPD instance of port {}", mpdInstancePortNumber);
        }
    }

    /**
     * Issues a stop command to the appropriate MPD instance.
     *
     * @param mpdInstancePortNumber the MPD instance port number
     */
    public void stop(final int mpdInstancePortNumber) {
        final Player mpdPlayer = getMPDPlayer(mpdInstancePortNumber);
        try {
            mpdPlayer.stop();
        } catch (MPDPlayerException e) {
            LOGGER.error("Error when handling stop command for MPD instance of port {}", mpdInstancePortNumber);
        }
    }

    /**
     * Listens to the MPD instance related to a zone.
     *
     * @param zone a zone
     */
    public void listenTo(final Zone zone) {
        final MPD mpd = mpdProvider.getMPD(zone.getMpdInstancePortNumber());
        final StandAloneMonitor monitor = mpd.getMonitor();
        monitor.addPlayerChangeListener(new MPDListener(zone.getId(), eventBus, zoneRepository));
        monitor.start();
    }

    /**
     * Returns the MPD Player for a zone.
     *
     * @param mpdInstancePortNumber the port number of the MPD instance
     * @return the {@link Player} instance related to the zone named {@code zone}
     */
    private Player getMPDPlayer(final int mpdInstancePortNumber) {
        return mpdProvider.getMPD(mpdInstancePortNumber).getPlayer();
    }

}