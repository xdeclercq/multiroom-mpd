package com.autelhome.multiroom.util;

import java.util.UUID;

/**
 * Repository to manage the storage of aggregate roots.
 *
 * @author xdeclercq
 *
 * @param <T> the aggregate root type
 */
public interface Repository<T extends AggregateRoot> {

    /**
     * Saves the uncommitted changes of an aggregate root if the expected version
     * matches the latest version known by the repository.
     *
     * @param aggregateRoot an aggregate root
     * @param expectedVersion the expected version of the aggregate root
     */
    void save(T aggregateRoot, int expectedVersion);

    /**
     * Returns an aggregate root by its id.
     *
     * @param id an aggregate root id
     * @return the aggregate with id {@code id}
     */
    T getById(UUID id);
}
