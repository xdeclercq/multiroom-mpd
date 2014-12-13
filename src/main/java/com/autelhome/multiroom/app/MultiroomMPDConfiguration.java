package com.autelhome.multiroom.app;

import com.autelhome.multiroom.zone.ZonesConfiguration;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import java.net.URI;

/**
 * Configuration of the application.
 *
 * @author xavier
 */
public class MultiroomMPDConfiguration extends Configuration
{
    private final URI baseURI;
    private final String mpdServer;
    private final ZonesConfiguration zonesConfiguration;

    /**
     * Constructor.
     *
     * @param baseURI the base URI of the application used to build the links to resource representations
     * @param mpdServer the MPD server URL
     * @param zonesConfiguration the {@link ZonesConfiguration}
     */
    @JsonCreator
    public MultiroomMPDConfiguration(@JsonProperty("baseURI") String baseURI,
                                     @JsonProperty("mpdServer") String mpdServer,
                                     @JsonProperty("zones") ZonesConfiguration zonesConfiguration)
    {
        this.baseURI = URI.create(baseURI);
        this.mpdServer = mpdServer;
        this.zonesConfiguration = zonesConfiguration;
    }

    public URI getBaseURI()
    {
        return baseURI;
    }

    public String getMpdServer()
    {
        return mpdServer;
    }

    public ZonesConfiguration getZonesConfiguration()
    {
        return zonesConfiguration;
    }
}
