package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.player.CurrentSong;
import com.autelhome.multiroom.player.PlayerStatus;
import com.autelhome.multiroom.playlist.ZonePlaylist;
import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneRepository;
import com.google.inject.Inject;
import java.util.List;
import java.util.Optional;
import org.bff.javampd.MPD;
import org.bff.javampd.Player;
import org.bff.javampd.Playlist;
import org.bff.javampd.StandAloneMonitor;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.exception.MPDPlaylistException;
import org.bff.javampd.objects.MPDSong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Gateway to MPD instances.
 *
 * @author xdeclercq
 */
public class MPDGateway {
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
    public MPDGateway(final MPDProvider mpdProvider, final EventBus eventBus, final ZoneRepository zoneRepository) {
        this.mpdProvider = mpdProvider;
        this.eventBus = eventBus;
        this.zoneRepository = zoneRepository;
    }

    /**
     * Returns the player current status.
     *
     * @param mpdInstancePortNumber the MPD instance port number
     * @return the player current status
     */
    public PlayerStatus getPlayerStatus(final int mpdInstancePortNumber) {
        final Player mpdPlayer = getMPDPlayer(mpdInstancePortNumber);
        try {
            return PlayerStatus.fromMPDPlayerStatus(mpdPlayer.getStatus());
        } catch (MPDPlayerException e) {
            LOGGER.error("Error when looking of MPD player with port {}", mpdInstancePortNumber, e);
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
            LOGGER.error("Error when handling play command for MPD instance of port {}", mpdInstancePortNumber, e);
        }
    }

    /**
     * Issues a play command to the appropriate MPD instance.
     *
     * @param mpdInstancePortNumber the MPD instance port number
     * @param position the position of the song to play
     */
    public void playSongAtPosition(final int mpdInstancePortNumber, final int position) {
        final Player mpdPlayer = getMPDPlayer(mpdInstancePortNumber);
        try {
            final MPD mpd = mpdProvider.getMPD(mpdInstancePortNumber);
            final Playlist mpdPlaylist = mpd.getPlaylist();
            final List<MPDSong> mpdSongs = mpdPlaylist.getSongList();
            final int index = position - 1;
            if (index < 0 || index >= mpdSongs.size()) {
                LOGGER.error("Error when handling play song command for MPD instance of port {}: "
                        + "position (%d) out of playlist", mpdInstancePortNumber, position);
                return;
            }
            final MPDSong mpdSong = mpdSongs.get(index);
            mpdPlayer.playId(mpdSong);
        } catch (MPDPlayerException | MPDPlaylistException e) {
            LOGGER.error("Error when handling play song command for MPD instance of port {}", mpdInstancePortNumber, e);
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
            LOGGER.error("Error when handling pause command for MPD instance of port {}", mpdInstancePortNumber, e);
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
            LOGGER.error("Error when handling stop command for MPD instance of port {}", mpdInstancePortNumber, e);
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
        monitor.addPlayerChangeListener(new MPDPlayerChangeListener(zone.getId(), eventBus, zoneRepository));
        monitor.addPlaylistChangeListener(new MPDPlaylistChangeListener(zone.getId(), eventBus, zoneRepository, this));
        monitor.start();
    }

    /**
     * Returns the playlist for a zone.
     *
     * @param mpdInstancePortNumber the MPD instance port number
     * @return the {@link ZonePlaylist} related to the MPD instance with port number {@code mpdInstancePortNumber}
     */
    public ZonePlaylist getZonePlaylist(final int mpdInstancePortNumber) {
        final MPD mpd = mpdProvider.getMPD(mpdInstancePortNumber);
        try {
            return ZonePlaylist.fromMPDPlaylist(mpd.getPlaylist());
        } catch (final MPDException e) {
            LOGGER.error("Error when fetching zone playlist for MPD instance of port {}", mpdInstancePortNumber, e);
        }
        return new ZonePlaylist();
    }

    /**
     * Returns the current song for a zone.
     *
     * @param mpdInstancePortNumber the MPD instance port number
     * @return the {@link CurrentSong} related to the MPD instance with port number {@code mpdInstancePortNumber}
     */
    public Optional<CurrentSong> getCurrentSong(final int mpdInstancePortNumber) {
        final MPD mpd = mpdProvider.getMPD(mpdInstancePortNumber);
        try {
            final MPDSong mpdCurrentSong = mpd.getPlayer().getCurrentSong();
            return mpdCurrentSong == null ? Optional.<CurrentSong>empty() : Optional.of(CurrentSong.fromMPDSong(mpdCurrentSong));
        } catch (final MPDPlayerException e) {
            LOGGER.error("Error when fetching current song for MPD instance of port {}", mpdInstancePortNumber, e);
        }
        return Optional.empty();
    }

    /**
     * Returns the MPD Player for a zone.
     *
     * @param mpdInstancePortNumber the port number of the MPD instance
     * @return the {@link Player} related to the MPD instance with port number {@code mpdInstancePortNumber}
     */
    private Player getMPDPlayer(final int mpdInstancePortNumber) {
        return mpdProvider.getMPD(mpdInstancePortNumber).getPlayer();
    }
}
