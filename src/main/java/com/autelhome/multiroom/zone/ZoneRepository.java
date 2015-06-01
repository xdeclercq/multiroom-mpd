package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.util.AbstractRepository;
import com.autelhome.multiroom.util.EventStore;
import com.google.inject.Inject;

/**
 * Zone repository.
 *
 * @author xdeclercq
 */
public class ZoneRepository extends AbstractRepository<Zone> {

    /**
     * Constructor.
     *
     * @param eventStore the event store
     */
    @Inject
    public ZoneRepository(final EventStore eventStore) {
        super(eventStore);
    }

}
