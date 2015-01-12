package com.autelhome.multiroom.zone;

/**
 * Represents a zone.
 *
 * A zone is a location in your house which is associated to its own player and playlist.
 *
 * @author xdeclercq
 */
public class Zone implements Comparable<Zone>
{

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

        if (name == null ? zone.name != null : !name.equals(zone.name))
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return name == null ?  0 : name.hashCode();
    }

    @Override
    public int compareTo(final Zone other) {
        return name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return "Zone{" +
                "name='" + name + '\'' +
                '}';
    }
}
