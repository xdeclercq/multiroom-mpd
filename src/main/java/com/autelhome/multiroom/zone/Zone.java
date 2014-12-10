package com.autelhome.multiroom.zone;

import com.sun.istack.internal.NotNull;

/**
 * Represents a zone.
 *
 * A zone is a location in your house which is associated to its own player and playlist.
 *
 * @author xdeclercq
 */
public class Zone implements Comparable<Zone>
{

    @NotNull
    private final String name;

    /**
     * Constructor.
     *
     * @param name the name
     */
    public Zone(final String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        final Zone zone = (Zone) o;

        if (name != null ? !name.equals(zone.name) : zone.name != null)
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public int compareTo(final Zone other) {
        return name.compareTo(other.name);
    }
}