package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.player.PlayerStatus;
import com.autelhome.multiroom.playlist.PlaylistSong;
import com.autelhome.multiroom.playlist.ZonePlaylist;
import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.util.Event;
import com.autelhome.multiroom.util.InstanceNotFoundException;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ZonesViewTest {

    private final ZoneDatabase zoneDatabase = mock(ZoneDatabase.class);
    private final ZonesView testSubject = new ZonesView(zoneDatabase);

    @Test
    public void handleCreated() throws Exception {
        final UUID id = UUID.randomUUID();

        final String name = "a zone";
        final int mpdInstancePortNumber = 1984;
        final PlayerStatus playerStatus = PlayerStatus.PLAYING;
        final int version = 543;
        final ZoneCreated zoneCreated = new ZoneCreated(id, name, mpdInstancePortNumber, playerStatus, new ZonePlaylist(Arrays.asList(new PlaylistSong(new Song("a"), 1), new PlaylistSong(new Song("b"), 2))));
        zoneCreated.setVersion(543);

        testSubject.handleCreated(zoneCreated);

        verify(zoneDatabase, times(1)).add(new ZoneDto(id, name, mpdInstancePortNumber, version));
    }

    @Test
    public void handleDefault() throws Exception {
        final Event event = mock(Event.class);
        final UUID id = UUID.randomUUID();
        when(event.getId()).thenReturn(id);
        final String name = "some zone";
        final int mpdInstancePortNumber = 3749;
        when(zoneDatabase.getById(id)).thenReturn(Optional.of(new ZoneDto(id, name, mpdInstancePortNumber, 3)));
        final int version = 993;
        when(event.getVersion()).thenReturn(version);

        testSubject.handleDefault(event);

        verify(zoneDatabase).update(new ZoneDto(id, name, mpdInstancePortNumber, version));
    }

    @Test(expected = InstanceNotFoundException.class)
    public void handleDefaultNotFound() throws Exception {
        final Event event = mock(Event.class);
        final UUID id = UUID.randomUUID();
        when(event.getId()).thenReturn(id);
        when(zoneDatabase.getById(id)).thenReturn(Optional.empty());
        final int version = 993;
        when(event.getVersion()).thenReturn(version);

        testSubject.handleDefault(event);
    }
}