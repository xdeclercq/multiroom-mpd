package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.util.Event;
import com.autelhome.multiroom.util.InstanceNotFoundException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Optional;
import java.util.UUID;

/**
 * View for a zones.
 *
 * @author xdeclercq
 */
@Singleton
public class ZonesView {

    private final ZoneDatabase zoneDatabase;

    /**
     * Constructor.
     *
     * @param zoneDatabase the zone database
     */
    @Inject
    public ZonesView(final ZoneDatabase zoneDatabase) {
        this.zoneDatabase = zoneDatabase;
    }

    /**
     * Updates database with a created zone.
     *
     * @param zoneCreated a zone created event
     */
    public void handleCreated(final ZoneCreated zoneCreated) {
        zoneDatabase.add(new ZoneDto(zoneCreated.getId(), zoneCreated.getName(), zoneCreated.getMpdInstancePortNumber(), zoneCreated.getVersion()));
    }

    /**
     * Updates database with a zone related event.
     *
     * @param event an event
     */
    public void handleDefault(final Event event) {
        final UUID id = event.getId();
        final Optional<ZoneDto> zoneOptional = zoneDatabase.getById(id);
        if (!zoneOptional.isPresent()) {
            throw new InstanceNotFoundException("The zone '" + id + "' is not present in the database");
        }
        final ZoneDto zone = zoneOptional.get();
        final ZoneDto newZone = new ZoneDto(id, zone.getName(), zone.getMpdInstancePortNumber(), event.getVersion());
        zoneDatabase.update(newZone);
    }
}
