package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.player.PlayerStatus;
import com.autelhome.multiroom.util.Event;
import com.autelhome.multiroom.util.EventStore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ZoneRepositoryTest {

    private final EventStore eventStore = mock(EventStore.class);
    private final ZoneRepository testSubject = new ZoneRepository(eventStore);


    @Test
    public void save() throws Exception {
        final Zone zone = mock(Zone.class);
        final int expectedVersion = 1;
        final List<Event> uncommittedChanges = mock(List.class);
        when(zone.getUncommittedChanges()).thenReturn(uncommittedChanges);
        final UUID id = UUID.randomUUID();
        when(zone.getId()).thenReturn(id);

        testSubject.save(zone, expectedVersion);
        
        verify(eventStore, times(1)).saveEvents(id, uncommittedChanges, expectedVersion);
    }

    @Test
    public void getById() {
        final UUID id = UUID.randomUUID();
        final String name = "a zone";
        final int mpdInstancePortNumber = 1984;
        final PlayerStatus playerStatus = PlayerStatus.STOPPED;
        final Zone expected = new Zone(id, name, mpdInstancePortNumber, playerStatus);
        when(eventStore.getEventsForAggregate(id)).thenReturn(Arrays.asList(new ZoneCreated(id, name, mpdInstancePortNumber, playerStatus)));
        final Zone actual = testSubject.getById(id);
        assertThat(actual).isEqualTo(expected);
    }
}