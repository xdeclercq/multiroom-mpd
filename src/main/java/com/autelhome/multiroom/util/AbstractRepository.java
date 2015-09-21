package com.autelhome.multiroom.util;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.UUID;

/**
 * Abstract aggregate repository.
 *
 * @author xdeclercq
 *
 * @param <T> the aggregate root type
 */
public abstract class AbstractRepository<T extends AggregateRoot> implements Repository<T> {

    private final EventStore eventStore;

    /**
     * Constructor.
     *
     * @param eventStore the event store
     */
    protected AbstractRepository(final EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public void save(final T aggregateRoot, final int expectedVersion) {
        eventStore.saveEvents(aggregateRoot.getId(), aggregateRoot.getUncommittedChanges(), expectedVersion);
        aggregateRoot.markChangesAsCommited();
    }

    @Override
    public T getById(final UUID id) {
        final Class<T> aggregateRootClass = (Class<T>)
                ((ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];

        final T aggregateRoot;
        try {
            aggregateRoot = aggregateRootClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new AggregateInstantiationException(id, e);
        }

        final List<Event> events = eventStore.getEventsForAggregate(id);

        aggregateRoot.loadFromHistory(events);
        return aggregateRoot;
    }

}
