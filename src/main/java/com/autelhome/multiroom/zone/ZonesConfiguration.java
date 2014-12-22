package com.autelhome.multiroom.zone;

import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Configuration of the zones.
 *
 * @author xdeclercq
 */
public class ZonesConfiguration extends HashSet<ZoneConfiguration>
{
    private static final long serialVersionUID = -4108852752779949992L;

    /**
     * Returns the sorted set of zones.
     *
     * @return the sorted set of {@link Zone}s.
     */
    public SortedSet<Zone> toZones()
    {
        return stream()
                .map(zoneConfiguration -> zoneConfiguration.toZone())
                .collect(Collectors.toCollection(TreeSet::new));
    }

}
