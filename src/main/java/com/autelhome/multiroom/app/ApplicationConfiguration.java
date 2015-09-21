package com.autelhome.multiroom.app;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

/**
 * Configuration of the application.
 *
 * @author xdeclercq
 */
public final class ApplicationConfiguration extends Configuration {
    private final String mpdHost;

    /**
     * Constructor.
     *
     * @param mpdHost the MPD hostname or IP address
     */
    @JsonCreator
    public ApplicationConfiguration(@JsonProperty("mpdHost") final String mpdHost) {
        this.mpdHost = mpdHost;
    }

    /**
     * Returns the mpd host.
     *
     * @return the mpd host
     */
    public String getMPDHost() {
        return mpdHost;
    }


}
