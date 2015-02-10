package com.autelhome.multiroom.app;

import com.autelhome.multiroom.zone.ZonesConfiguration;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

/**
 * Configuration of the application.
 *
 * @author xdeclercq
 */
public class ApplicationConfiguration extends Configuration
{
    private final ZonesConfiguration zonesConfiguration;
    private final String mpdHost;

    /**
     * Constructor.
     *
     * @param mpdHost the MPD hostname or IP address
     * @param zonesConfiguration the {@link ZonesConfiguration}
     */
    @JsonCreator
    public ApplicationConfiguration(@JsonProperty("mpdHost") final String mpdHost,
                                    @JsonProperty("zones") final ZonesConfiguration zonesConfiguration)
    {
        this.mpdHost = mpdHost;
        this.zonesConfiguration = zonesConfiguration;
    }

    /**
     * Returns the mpd host.
     *
     * @return the mpd host
     */
    public String getMPDHost() {
        return mpdHost;
    }

    /**
     * Returns the zones configuration.
     *
     * @return the {@link ZonesConfiguration}
     */
    public ZonesConfiguration getZonesConfiguration()
    {
        return zonesConfiguration;
    }

}
