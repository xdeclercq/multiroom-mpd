package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.player.ChangeCurrentSong;
import com.autelhome.multiroom.playlist.ChangeZonePlaylist;
import com.autelhome.multiroom.playlist.ZonePlaylist;
import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneRepository;
import org.bff.javampd.events.PlaylistBasicChangeEvent;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class MPDPlaylistChangeListenerTest {
    private static final UUID ZONE_ID = UUID.randomUUID();
    private static final int ZONE_VERSION = 345;
    private static final int MPD_INSTANCE_PORT_NUMBER = 7343;
    private final EventBus eventBus = mock(EventBus.class);
    private final ZoneRepository zoneRepository = mock(ZoneRepository.class);
    private final MPDGateway mpdGateway = mock(MPDGateway.class);
    private final MPDPlaylistChangeListener testSubject = new MPDPlaylistChangeListener(ZONE_ID, eventBus, zoneRepository, mpdGateway);

    @Test
    public void playlistBasicChangeForPlaylistChangedEvent() throws Exception {
        final PlaylistBasicChangeEvent event = mock(PlaylistBasicChangeEvent.class);
        when(event.getEvent()).thenReturn(PlaylistBasicChangeEvent.Event.PLAYLIST_CHANGED);

        final Zone zone = mock(Zone.class);
        when(zoneRepository.getById(ZONE_ID)).thenReturn(zone);
        when(zone.getVersion()).thenReturn(ZONE_VERSION);
        when(zone.getMpdInstancePortNumber()).thenReturn(MPD_INSTANCE_PORT_NUMBER);
        when(zone.getId()).thenReturn(ZONE_ID);
        final ZonePlaylist newPlaylist = new ZonePlaylist(Arrays.asList(new Song("Song A"), new Song("Song B")));
        when(mpdGateway.getZonePlaylist(MPD_INSTANCE_PORT_NUMBER)).thenReturn(newPlaylist);
        testSubject.playlistBasicChange(event);

        verify(eventBus).send(new ChangeZonePlaylist(ZONE_ID, newPlaylist, ZONE_VERSION));
    }

    @Test
    public void playlistBasicChangeForSongChangedEvent() throws Exception {
        final PlaylistBasicChangeEvent event = mock(PlaylistBasicChangeEvent.class);
        when(event.getEvent()).thenReturn(PlaylistBasicChangeEvent.Event.SONG_CHANGED);

        final Zone zone = mock(Zone.class);
        when(zoneRepository.getById(ZONE_ID)).thenReturn(zone);
        when(zone.getVersion()).thenReturn(ZONE_VERSION);
        when(zone.getMpdInstancePortNumber()).thenReturn(MPD_INSTANCE_PORT_NUMBER);
        when(zone.getId()).thenReturn(ZONE_ID);
        final Song newCurrentSong = new Song("Song A");
        when(mpdGateway.getCurrentSong(MPD_INSTANCE_PORT_NUMBER)).thenReturn(Optional.of(newCurrentSong));
        testSubject.playlistBasicChange(event);

        verify(eventBus).send(new ChangeCurrentSong(ZONE_ID, newCurrentSong, ZONE_VERSION));
    }
}