package com.autelhome.multiroom.util;

import java.util.List;
import java.util.UUID;

/**
 * Aggregate root for domain objects.
 *
 * @author xdeclercq
 */
public interface AggregateRoot {

    /**
     * Returns the list of uncommited changes.
     *
     * @return the list of uncommited changes.
     */
    List<Event> getUncommittedChanges();

    /**
     * Returns the id.
     *
     * @return the id
     */
    UUID getId();

    /**
     * Returns the version.
     *
     * @return the version
     */
    int getVersion();

    /**
     * Applies the change described by an event.
     *
     * @param event an event
     */
    void applyChange(Event event);

    /**
     * Load the aggregate from history by applying all the past events.
     *
     * @param history the history of events
     */
    void loadFromHistory(List<Event> history);

    /**
     * Marks the changes as commited.
     */
    void markChangesAsCommited();
}
