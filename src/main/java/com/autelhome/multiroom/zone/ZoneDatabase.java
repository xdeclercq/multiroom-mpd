package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.util.InstanceAlreadyPresentException;
import com.autelhome.multiroom.util.InstanceNotFoundException;
import com.google.inject.Singleton;

import java.util.*;

/**
 * Database to store zones.
 *
 * @author xdeclercq
 */
@Singleton
public class ZoneDatabase {

    private final Map<String, ZoneDto> zonesByName = new HashMap<>();
    private final Map<UUID, ZoneDto> zonesById = new HashMap<>();
    private final SortedSet<ZoneDto> zones = new TreeSet<>();

    /**
     * Adds a zone to the database.
     *
     * @param zoneDto a zone
     */
    public void add(final ZoneDto zoneDto) {
        if (zonesById.containsKey(zoneDto.getId()) || zonesByName.containsKey(zoneDto.getName())) {
            throw new InstanceAlreadyPresentException("The zone '" + zoneDto.getId() + "' is already present in the database");
        }

        zones.add(zoneDto);
        zonesByName.put(zoneDto.getName(), zoneDto);
        zonesById.put(zoneDto.getId(), zoneDto);
    }

    /**
     * Updates a zone to the database.
     *
     * @param zoneDto a zone
     */
    public void update(final ZoneDto zoneDto) {
        final UUID id = zoneDto.getId();
        final ZoneDto currentZone = zonesById.get(id);
        if (currentZone == null) {
            throw new InstanceNotFoundException("The zone '" + id + "' is not present in the database");
        }

        zones.remove(currentZone);
        zonesByName.remove(currentZone.getName());

        zones.add(zoneDto);
        zonesByName.put(zoneDto.getName(), zoneDto);
        zonesById.put(id, zoneDto);
    }

    /**
     * Returns the sorted set of zones.
     *
     * @return the sorted set of zones
     */
    public SortedSet<ZoneDto> getAll() {
        return Collections.unmodifiableSortedSet(zones);
    }

    /**
     * Returns a zone by its name.
     *
     * @param zoneName the name of a zone
     * @return a zone by its name
     */
    public Optional<ZoneDto> getByName(final String zoneName) {
        return Optional.ofNullable(zonesByName.get(zoneName));
    }

    /**
     * Returns a zone by its id.
     *
     * @param id a zone id
     * @return a zone by its id
     */
    public Optional<ZoneDto> getById(final UUID id) {
        return Optional.ofNullable(zonesById.get(id));
    }
}
