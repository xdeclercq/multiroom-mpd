package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.player.PlayerStatus;
import com.autelhome.multiroom.playlist.PlaylistSong;
import com.autelhome.multiroom.playlist.ZonePlaylist;
import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.util.Event;
import com.autelhome.multiroom.util.EventStore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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
        final ZonePlaylist playlist = new ZonePlaylist(Arrays.asList(new PlaylistSong(new Song("a"), 1), new PlaylistSong(new Song("b"), 2)));
        final Zone expected = new Zone(id, name, mpdInstancePortNumber, playerStatus, playlist);
        when(eventStore.getEventsForAggregate(id)).thenReturn(Arrays.asList(new ZoneCreated(id, name, mpdInstancePortNumber, playerStatus, playlist)));
        final Zone actual = testSubject.getById(id);
        assertThat(actual).isEqualTo(expected);
    }
}