package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.playlist.ChangeZonePlaylist;
import com.autelhome.multiroom.playlist.ZonePlaylist;
import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneRepository;
import org.bff.javampd.events.PlaylistBasicChangeEvent;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class MPDPlaylistChangeListenerTest {
    private final EventBus eventBus = mock(EventBus.class);
    private final ZoneRepository zoneRepository = mock(ZoneRepository.class);
    private final MPDGateway mpdGateway = mock(MPDGateway.class);
    private final UUID zoneId = UUID.randomUUID();
    private final MPDPlaylistChangeListener testSubject = new MPDPlaylistChangeListener(zoneId, eventBus, zoneRepository, mpdGateway);

    @Test
    public void playerBasicChange() throws Exception {
        final PlaylistBasicChangeEvent event = mock(PlaylistBasicChangeEvent.class);

        final int zoneVersion = 345;
        final Zone zone = mock(Zone.class);
        when(zoneRepository.getById(zoneId)).thenReturn(zone);
        when(zone.getVersion()).thenReturn(zoneVersion);
        final int mpdInstancePortNumber = 7343;
        when(zone.getMpdInstancePortNumber()).thenReturn(mpdInstancePortNumber);
        when(zone.getId()).thenReturn(zoneId);
        final ZonePlaylist newPlaylist = new ZonePlaylist(Arrays.asList(new Song("Song A"), new Song("Song B")));
        when(mpdGateway.getZonePlaylist(mpdInstancePortNumber)).thenReturn(newPlaylist);
        testSubject.playlistBasicChange(event);

        verify(eventBus).send(new ChangeZonePlaylist(zoneId, newPlaylist, zoneVersion));
    }
}