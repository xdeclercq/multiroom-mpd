package com.autelhome.multiroom.player;

import com.autelhome.multiroom.mpd.MPDPool;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZonesConfiguration;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bff.javampd.exception.MPDPlayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provider of player by its zone.
 *
 * @author xdeclercq
 */
@Singleton
public class PlayerProvider
{

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerProvider.class);
    private final Map<Zone, Player> players;

    /**
     * Constructor.
     *
     * @param zonesConfiguration the {@link ZonesConfiguration}
     * @param mpdPool the {@link MPDPool}
     */
    @Inject
    public PlayerProvider(final ZonesConfiguration zonesConfiguration, final MPDPool mpdPool)
    {
        players =  zonesConfiguration.toZones()
                .stream()
                .map(zone -> {
                    final org.bff.javampd.Player mpdPlayer = mpdPool.getMPDPlayer(zone);
                    try {
                        final org.bff.javampd.Player.Status mpdStatus = mpdPlayer.getStatus();
                        return new Player(zone, PlayerStatus.fromMPDPlayerStatus(mpdStatus));
                    } catch (MPDPlayerException e) {
                        LOGGER.warn("Unable to get the status of the player of zone " + zone.getName(), e);
                        return new Player(zone, PlayerStatus.STOPPED);
                    }
                })
                    .collect(Collectors.toMap(Player::getZone, player -> player));
                }

    /**
     * Returns the player related to a zone.
     *
     * @param zone a zone
     * @return the player related to the provided {@code zone}
     */
    public Player getPlayer(final Zone zone)
    {
        return players.get(zone);
    }
}
