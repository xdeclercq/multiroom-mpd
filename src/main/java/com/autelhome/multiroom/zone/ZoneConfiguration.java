package com.autelhome.multiroom.zone;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Configuration of a zone.
 *
 * @author xdeclercq
 */
public class ZoneConfiguration
{
    private final String name;
    private final int mpdPort;

    /**
     * Constructor.
     *
     * @param name the zone name
     * @param mpdPort the MPD instance port number
     */
    @JsonCreator
    public ZoneConfiguration(@JsonProperty("name") String name, @JsonProperty("mpdPort") int mpdPort)
    {
        this.name = name;
        this.mpdPort = mpdPort;
    }

    public String getName()
    {
        return name;
    }

    public int getMPDPort()
    {
        return mpdPort;
    }

    /**
     * Returns the zone from its configuration.
     *
     * @return the zone
     */
    public Zone toZone()
    {
        return new Zone(name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ZoneConfiguration that = (ZoneConfiguration) o;

        if (mpdPort != that.mpdPort) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + mpdPort;
        return result;
    }
}
