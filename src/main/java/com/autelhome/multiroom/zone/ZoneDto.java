package com.autelhome.multiroom.zone;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a zone data transfer object.
 *
 * A zone is a location in your house which is associated to its own player and playlist.
 *
 * @author xdeclercq
 */
public class ZoneDto implements Comparable<ZoneDto>
{

    private final String name;
    private final UUID id;
    private final int mpdInstancePortNumber;
    private final int version;

    /**
     * Constructor.
     *
     * @param id the id
     * @param name the name
     * @param mpdInstancePortNumber MPD instance port number
     * @param version the zone version
     */
    public ZoneDto(final UUID id, final String name, final int mpdInstancePortNumber, final int version)
    {
        this.id = id;
        this.name = name;
        this.mpdInstancePortNumber = mpdInstancePortNumber;
        this.version = version;
    }

    public String getName()
    {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public int getMpdInstancePortNumber() {
        return mpdInstancePortNumber;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ZoneDto zoneDto = (ZoneDto) o;

        if (mpdInstancePortNumber != zoneDto.mpdInstancePortNumber) {
            return false;
        }
        if (id == null ? zoneDto.id != null : !id.equals(zoneDto.id)) {
            return false;
        }
        if (name == null ? zoneDto.name != null : !name.equals(zoneDto.name)) {
            return false;
        }
        if (version != zoneDto.version) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mpdInstancePortNumber, version);
    }

    @Override
    public int compareTo(final ZoneDto other) {
        final int nameComparison = name.compareTo(other.name);
        if (nameComparison!=0) {
            return nameComparison;
        }

        return id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("mpdInstancePortNumber", mpdInstancePortNumber)
                .add("version", version)
                .toString();
    }
}
