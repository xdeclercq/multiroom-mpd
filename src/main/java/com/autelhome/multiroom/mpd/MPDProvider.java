package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.app.ApplicationConfiguration;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.bff.javampd.MPD;
import org.bff.javampd.exception.MPDConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Pool of MPD clients.
 *
 * @author xdeclercq
 */
@Singleton
public class MPDProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(MPDProvider.class);
    private final Map<Integer, MPD> mpds = new HashMap<>();
    private final String mpdHost;

    /**
     * Constructor.
     *
     * @param configuration the application configuration
     */
    @Inject
    public MPDProvider(final ApplicationConfiguration configuration) {
        this.mpdHost = configuration.getMPDHost();
    }


    /**
     * Returns the {@link MPD} instance related the given port number.
     *
     * @param mpdInstancePortNumber an MPD instance port number
     * @return the {@link MPD} instance related to {@code mpdInstancePortNumber}
     */
    public MPD getMPD(final int mpdInstancePortNumber) {
        final boolean isPresent = mpds.containsKey(mpdInstancePortNumber);
        try {
            final MPD mpd = isPresent ? mpds.get(mpdInstancePortNumber) : new MPD.Builder().server(mpdHost).port(mpdInstancePortNumber).build();
            if (!isPresent) {
                mpds.put(mpdInstancePortNumber, mpd);
            }
            return mpd;
        } catch (UnknownHostException | MPDConnectionException e) {
            LOGGER.error("Failed to connect to MPD server {} on port {}", new Object[]{ mpdHost, mpdInstancePortNumber });
            throw new MPDException("Unable to connect to MPD server", e);
        }
    }

}
