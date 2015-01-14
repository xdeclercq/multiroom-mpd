package com.autelhome.multiroom.player;

import com.autelhome.multiroom.mpd.MPDPool;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZonesConfiguration;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bff.javampd.exception.MPDPlayerException;

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
                    try {
                        final org.bff.javampd.Player mpdPlayer = mpdPool.getMPDPlayer(zone);
                        final org.bff.javampd.Player.Status mpdStatus = mpdPlayer.getStatus();
                        return new Player(zone, PlayerStatus.fromMPDPlayerStatus(mpdStatus));
                    } catch (MPDPlayerException e) {
                        throw new PlayerException("Unable to get the status of the player of zone " + zone.getName(), e);
                    }
                })
                    .collect(Collectors.toMap(Player::getZone, player -> player));
                }

    /**
     * Returns the player related to a zone.
     *
     * @param zoneName a zone name
     * @return the player related to the zone named {@code zoneName}
     */
    public Player getPlayer(final String zoneName)
    {
        return players.get(new Zone(zoneName));
    }

}
