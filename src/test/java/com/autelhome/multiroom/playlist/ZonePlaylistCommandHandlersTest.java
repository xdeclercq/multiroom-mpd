package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneRepository;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ZonePlaylistCommandHandlersTest {
    private static final UUID ZONE_ID = UUID.randomUUID();
    private final ZoneRepository zoneRepository = mock(ZoneRepository.class);
    private final ZonePlaylistCommandHandlers testSubject = new ZonePlaylistCommandHandlers(zoneRepository);

    @Test
    public void handleChangeZonePlaylist() throws Exception {
        final Zone zone = mock(Zone.class);
        when(zoneRepository.getById(ZONE_ID)).thenReturn(zone);
        testSubject.handleChangeZonePlaylist(new ChangeZonePlaylist(ZONE_ID, new ZonePlaylist(Arrays.asList(new PlaylistSong(new Song("song 1"), 1))), 123));
        verify(zoneRepository, times(1)).save(zone, 123);
    }
}