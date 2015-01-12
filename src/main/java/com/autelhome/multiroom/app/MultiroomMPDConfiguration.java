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
public class MultiroomMPDConfiguration extends Configuration
{
    private final ZonesConfiguration zonesConfiguration;

    /**
     * Constructor.
     *
     * @param zonesConfiguration the {@link ZonesConfiguration}
     */
    @JsonCreator
    public MultiroomMPDConfiguration(@JsonProperty("zones") final ZonesConfiguration zonesConfiguration)
    {
        this.zonesConfiguration = zonesConfiguration;
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
