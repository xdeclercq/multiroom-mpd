package com.autelhome.multiroom.zone;

import com.google.inject.Inject;

import java.util.SortedSet;

/**
 * Service to manage {@link Zone}s.
 *
 * @author xdeclercq
 */
public class ZoneService {

    private final ZonesConfiguration zonesConfiguration;

    /**
     * Constructor.
     *
     * @param zonesConfiguration the {@link ZonesConfiguration}
     */
    @Inject
    public ZoneService(final ZonesConfiguration zonesConfiguration) {
        this.zonesConfiguration = zonesConfiguration;
    }

    /**
     * Returns the sorted set of zones.
     *
     * @return the sorted set of {@link Zone}s.
     */
    public SortedSet<Zone> getAll() {
        return zonesConfiguration.toZones();
    }

    /**
     * Retrieves a zone by its name.
     *
     * @param zoneName a zone name
     * @return the zone named {@code zoneName}
     */
    public Zone getByName(final String zoneName) {
        return getAll().stream().filter(zone -> zoneName.equals(zone.getName())).findFirst().get();
    }

}
