package com.autelhome.multiroom.util;

import java.util.List;
import java.util.UUID;

/**
 * Manages the storage and the retrieval of events.
 *
 * @author xdeclercq
 */
public interface EventStore {

    /**
     * Save events related to an aggregate in the store.
     *
     * @param aggregateId the aggregate id
     * @param events the events
     * @param expectedVersion the expected version
     */
    void saveEvents(UUID aggregateId, List<Event> events, int expectedVersion);

    /**
     * Returns the list of events related to an aggregate.
     *
     * @param aggregateId an aggregate id
     * @return the list of events related to the aggregate of id {@code aggregateId}
     */
    List<Event> getEventsForAggregate(UUID aggregateId);

}
