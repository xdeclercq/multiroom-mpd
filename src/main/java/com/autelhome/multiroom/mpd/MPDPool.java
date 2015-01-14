package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.app.ApplicationConfiguration;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneConfiguration;
import com.autelhome.multiroom.zone.ZonesConfiguration;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bff.javampd.MPD;
import org.bff.javampd.Player;
import org.bff.javampd.exception.MPDConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Pool of MPD clients.
 *
 * @author xdeclercq
 */
@Singleton
public class MPDPool
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MPDPool.class);
    private final Map<Zone, MPD> mpds;

    /**
     * Constructor.
     *
     * @param configuration the application configuration
     * @param zonesConfiguration the zones configuration
     */
    @Inject
    public MPDPool(final ApplicationConfiguration configuration, final ZonesConfiguration zonesConfiguration)
    {
        mpds = zonesConfiguration
                .stream()
                .collect(Collectors.toMap(ZoneConfiguration::toZone,
                        zoneConfiguration
                                ->
                        {
                            final String mpdHost = configuration.getMPDHost();
                            final int mpdPort = zoneConfiguration.getMPDPort();
                            try {
                                return new MPD.Builder().server(mpdHost).port(mpdPort).build();
                            } catch (UnknownHostException | MPDConnectionException e) {
                                LOGGER.error("Failed to connect to MPD server {} on port {} for zone {}", new Object[] { mpdHost, mpdPort, zoneConfiguration.getName() });
                                throw new MPDPoolException("Unable to connect to MPD server", e);
                            }
                        }));
    }

    /**
     * Returns the MPD Player for a zone.
     *
     * @param zone a zone name
     * @return the {@link Player} instance related to the zone named {@code zone}
     */
    public Player getMPDPlayer(final Zone zone)   {
        return mpds.get(zone).getPlayer();
    }

}
