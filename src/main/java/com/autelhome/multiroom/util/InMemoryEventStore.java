package com.autelhome.multiroom.util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * In memory event store.
 *
 * @author xdeclercq
 */
public class InMemoryEventStore implements EventStore {

    private final EventPublisher publisher;

    private final Map<UUID, List<EventDescriptor>> current = new HashMap<>();

    private static class EventDescriptor {
        private final Event eventData;
        private final int version;

        public EventDescriptor(final Event eventData, final int version) {
            this.eventData = eventData;
            this.version = version;
        }
    }

    /**
     * Constructor.
     *
     * @param publisher An {@link EventPublisher} instance.
     */
    public InMemoryEventStore(final EventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void saveEvents(final UUID aggregateId, final List<Event> events, final int expectedVersion) {
        final boolean containsAggregate = current.containsKey(aggregateId);
        final List<EventDescriptor> eventDescriptors = containsAggregate ? current.get(aggregateId) : new ArrayList<>();

        if (containsAggregate) {
            final int latestVersion = eventDescriptors.get(eventDescriptors.size() - 1).version;
            if (latestVersion != expectedVersion) {
                throw new ConcurrencyException("The expected version (" + expectedVersion + ") is not the latest one (" + latestVersion + ")");
            }
        } else {
            current.put(aggregateId, eventDescriptors);
        }

        IntStream.range(0, events.size())
                .forEach(i -> {
                    final Event event = events.get(i);
                    final int version = expectedVersion + i + 1;
                    event.setVersion(version);
                    eventDescriptors.add(new EventDescriptor(event, version));
                    publisher.publish(event);
                });

    }

    @Override
    public List<Event> getEventsForAggregate(final UUID aggregateId) {
        final List<EventDescriptor> eventDescriptors = current.get(aggregateId);

        if (eventDescriptors == null) {
            throw new AggregateNotFoundException(aggregateId);
        }

        return eventDescriptors.stream().map(eventDescriptor -> eventDescriptor.eventData).collect(Collectors.toList());
    }
}
