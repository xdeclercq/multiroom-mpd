package com.autelhome.multiroom.zone;

import com.google.inject.Inject;

import java.util.SortedSet;

/**
 * Service to manage {@link Zone}s.
 *
 * @author xavier
 */
public class ZoneService
{

    private final ZonesConfiguration zonesConfiguration;

    /**
     * Constructor.
     *
     * @param zonesConfiguration the {@link ZonesConfiguration}
     */
    @Inject
    public ZoneService(ZonesConfiguration zonesConfiguration)
    {
        this.zonesConfiguration = zonesConfiguration;
    }

    /**
     * Returns the sorted set of zones.
     *
     * @return the sorted set of {@link Zone}s.
     */
    public SortedSet<Zone> getAll()
    {
        return zonesConfiguration.toZones();
    }

}