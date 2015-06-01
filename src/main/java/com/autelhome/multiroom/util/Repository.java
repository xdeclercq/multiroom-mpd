package com.autelhome.multiroom.util;

import java.util.UUID;

/**
 * @author xdeclercq
 */
public interface Repository<T extends AggregateRoot> {

    void save(T aggregateRoot, int expectedVersion);

    T getById(UUID id);
}
