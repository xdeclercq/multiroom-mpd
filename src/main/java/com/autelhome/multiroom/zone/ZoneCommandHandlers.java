package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.mpd.MPDGateway;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Zone related command handlers.
 *
 * @author xdeclercq
 */
@Singleton
public class ZoneCommandHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZoneCommandHandlers.class);
    private final ZoneRepository repository;
    private final MPDGateway mpdGateway;

    /**
     * Constructor.
     *
     * @param repository the zone repository
     * @param mpdGateway the MPD pool
     */
    @Inject
    public ZoneCommandHandlers(final ZoneRepository repository, final MPDGateway mpdGateway) {
        this.repository = repository;
        this.mpdGateway = mpdGateway;
    }

    /**
     * Creates a zone and stores in the repository.
     *
     * @param createZone a create zone command
     */
    public void handleCreate(final CreateZone createZone) {
        final String zoneName = createZone.getName();
        LOGGER.info("[{}] - creating zone", zoneName);
        final int mpdInstancePortNumber = createZone.getMpdInstancePortNumber();

        final UUID id = createZone.getAggregateId();
        final Zone zone = new Zone(id, zoneName, mpdInstancePortNumber, mpdGateway.getPlayerStatus(mpdInstancePortNumber), mpdGateway.getZonePlaylist(mpdInstancePortNumber));
        mpdGateway.listenTo(zone);
        repository.save(zone, createZone.getOriginalVersion());
    }




}
