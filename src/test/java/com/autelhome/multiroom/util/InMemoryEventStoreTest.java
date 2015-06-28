package com.autelhome.multiroom.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class InMemoryEventStoreTest {

    private final EventPublisher publisher = mock(EventPublisher.class);
    private final InMemoryEventStore testSubject = new InMemoryEventStore(publisher);

    @Test
    public void saveEvents() throws Exception {
        final UUID aggregateId = UUID.randomUUID();
        final Event event1 = mock(Event.class);
        final Event event2 = mock(Event.class);

        final List<Event> events1 = Arrays.asList(event1, event2);
        testSubject.saveEvents(aggregateId, events1, 1984);

        verify(event1).setVersion(1985);
        verify(event2).setVersion(1986);
        verify(publisher).publish(event1);
        verify(publisher).publish(event2);

        final Event event3 = mock(Event.class);
        final Event event4 = mock(Event.class);
        final List<Event> events2 = Arrays.asList(event3, event4);
        testSubject.saveEvents(aggregateId, events2, 1986);

        verify(event3).setVersion(1987);
        verify(event4).setVersion(1988);
        verify(publisher).publish(event3);
        verify(publisher).publish(event4);
    }

    @Test(expected = ConcurrencyException.class)
    public void saveEventsNotLatestVersion() throws Exception {
        final UUID aggregateId = UUID.randomUUID();
        final Event event1 = mock(Event.class);
        final Event event2 = mock(Event.class);

        final List<Event> events1 = Arrays.asList(event1, event2);
        testSubject.saveEvents(aggregateId, events1, 1984);

        verify(event1).setVersion(1985);
        verify(event2).setVersion(1986);
        verify(publisher).publish(event1);
        verify(publisher).publish(event2);

        final Event event3 = mock(Event.class);
        final Event event4 = mock(Event.class);
        final List<Event> events2 = Arrays.asList(event3, event4);
        testSubject.saveEvents(aggregateId, events2, 2001);
    }

    @Test
    public void getEventsForAggregate() throws Exception {
        final Event event1 = mock(Event.class);
        final Event event2 = mock(Event.class);

        final List<Event> events1 = Arrays.asList(event1, event2);
        final UUID aggregateId1 = UUID.randomUUID();
        testSubject.saveEvents(aggregateId1, events1, 1984);

        final Event event3 = mock(Event.class);
        final Event event4 = mock(Event.class);
        final List<Event> events2 = Arrays.asList(event3, event4);
        final UUID aggregateId2 = UUID.randomUUID();
        testSubject.saveEvents(aggregateId2, events2, 1986);

        final Event event5 = mock(Event.class);
        final Event event6 = mock(Event.class);
        final List<Event> events3 = Arrays.asList(event5, event6);
        testSubject.saveEvents(aggregateId2, events3, 1988);

        final List<Event> actual1 = testSubject.getEventsForAggregate(aggregateId1);
        assertThat(actual1).isEqualTo(Arrays.asList(event1, event2));

        final List<Event> actual2 = testSubject.getEventsForAggregate(aggregateId2);
        assertThat(actual2).isEqualTo(Arrays.asList(event3, event4, event5, event6));

     }

    @Test(expected = AggregateNotFoundException.class)
    public void getEventsForUnknownAggregate() throws Exception {
        testSubject.getEventsForAggregate(UUID.randomUUID());
    }
}