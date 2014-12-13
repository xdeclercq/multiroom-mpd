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

    /**
     * Returns the sorted set of zones.
     *
     * @return the sorted set of zones.
     */
    public SortedSet<Zone> toZones()
    {
        return stream()
                .map(zoneConfiguration -> zoneConfiguration.toZone())
                .collect(Collectors.toCollection(TreeSet::new));
    }

}
