package com.autelhome.multiroom.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AbstractRepositoryTest {

    private final EventStore eventStore = mock(EventStore.class);
    private final Repository<MyAggregateRoot> testSubject = new MyAggregateRepository(eventStore);

    private static class MyAggregateRepository extends AbstractRepository<MyAggregateRoot> {
        protected MyAggregateRepository(final EventStore eventStore) {
            super(eventStore);
        }

    }

    private static class MyAggregateWithNonDefaultConstructorRepository extends AbstractRepository<AggregateRootWithNonDefaultConstructor> {
        protected MyAggregateWithNonDefaultConstructorRepository(final EventStore eventStore) {
            super(eventStore);
        }
    }

    private static class MyAggregateRootWithPrivateConstructorRepository extends AbstractRepository<AggregateRootWithPrivateConstructor> {
        protected MyAggregateRootWithPrivateConstructorRepository(final EventStore eventStore) {
            super(eventStore);
        }

    }

    private static class MyAggregateRoot extends AbstractAggregateRoot {
        public MyAggregateRoot() {
            // do nothing
        }

        @Override
        protected void apply(final Event event) {
            // do nothing
        }
    }
    private static class AggregateRootWithNonDefaultConstructor extends AbstractAggregateRoot {

        @SuppressWarnings("unused")
        public AggregateRootWithNonDefaultConstructor(final String something) {
            // do nothing
        }
        @Override
        protected void apply(final Event event) {
            // do nothing
        }

    }
    private static class AggregateRootWithPrivateConstructor extends AbstractAggregateRoot {

        private AggregateRootWithPrivateConstructor() {
            // do nothing
        }
        @Override
        protected void apply(final Event event) {
            // do nothing
        }
    }

    @Test
    public void save() throws Exception {
        final MyAggregateRoot aggregateRoot = mock(MyAggregateRoot.class);
        final int expectedVersion = 1;
        final List<Event> uncommittedChanges = mock(List.class);
        when(aggregateRoot.getUncommittedChanges()).thenReturn(uncommittedChanges);
        final UUID id = UUID.randomUUID();
        when(aggregateRoot.getId()).thenReturn(id);

        testSubject.save(aggregateRoot, expectedVersion);

        verify(eventStore, times(1)).saveEvents(id, uncommittedChanges, expectedVersion);
    }

    @Test
    public void getById() {
        final UUID id = UUID.randomUUID();

        final Event event1 = mock(Event.class);
        final Event event2 = mock(Event.class);
        final List<Event> events = Arrays.asList(event1, event2);
        when(eventStore.getEventsForAggregate(id)).thenReturn(events);
        when(event2.getVersion()).thenReturn(23);
        final AggregateRoot actual = testSubject.getById(id);
        assertThat(actual.getVersion()).isEqualTo(23);
    }

    @Test(expected = AggregateInstantiationException.class)
    public void getByIdWithNonDefaultConstrutor() throws Exception {
        final Repository<AggregateRootWithNonDefaultConstructor> testSubject = new MyAggregateWithNonDefaultConstructorRepository(eventStore);

        testSubject.getById(UUID.randomUUID());
    }

    @Test(expected = AggregateInstantiationException.class)
    public void getByIdWithPrivateConstrutor() throws Exception {
        final Repository<AggregateRootWithPrivateConstructor> testSubject = new MyAggregateRootWithPrivateConstructorRepository(eventStore);

        testSubject.getById(UUID.randomUUID());
    }
}