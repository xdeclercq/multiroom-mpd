package com.autelhome.multiroom.zone;

import com.google.inject.Inject;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Service to fetch {@link Zone}s.
 *
 * @author xdeclercq
 */
public class ZoneService {

    private final ZoneDatabase zoneDatabase;

    /**
     * Constructor.
     *
     * @param zoneDatabase the zone database
     */
    @Inject
    public ZoneService(final ZoneDatabase zoneDatabase) {
        this.zoneDatabase = zoneDatabase;
    }

    /**
     * Returns the sorted set of zones.
     *
     * @return the sorted set of {@link Zone}s.
     */
    public SortedSet<ZoneDto> getAll() {
        return new TreeSet<>(zoneDatabase.getAll());
    }

    /**
     * Retrieves a zone by its name.
     *
     * @param zoneName the name of a zone
     * @return the zone named {@code zoneName}
     */
    public Optional<ZoneDto> getByName(final String zoneName) {
        return zoneDatabase.getByName(zoneName);
    }

}
