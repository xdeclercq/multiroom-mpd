package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.player.PlayerStatus;
import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.util.InstanceNotFoundException;
import com.autelhome.multiroom.zone.ZoneCreated;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ZonePlaylistsViewTest {
    public static final String ZONE_NAME = "a zone";
    private final ZonePlaylistDatabase zonePlaylistDatabase = mock(ZonePlaylistDatabase.class);
    private final ZonePlaylistsView testSubject = new ZonePlaylistsView(zonePlaylistDatabase);

    @Test
    public void handleCreated() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final PlayerStatus playerStatus = PlayerStatus.STOPPED;
        final ZonePlaylist playlist = new ZonePlaylist(Arrays.asList(new Song("a"), new Song("b")));
        testSubject.handleCreated(new ZoneCreated(zoneId, ZONE_NAME, 2323, playerStatus, playlist));

        verify(zonePlaylistDatabase).add(new ZonePlaylistDto(zoneId, ZONE_NAME, playlist));
    }

    @Test
    public void handleZonePlaylistUpdated() throws Exception {
        final UUID zoneId = UUID.randomUUID();

        final Optional<ZonePlaylistDto> playlistDto = Optional.of(new ZonePlaylistDto(zoneId, ZONE_NAME, new ZonePlaylist(Arrays.asList(new Song("a"), new Song("b")))));
        when(zonePlaylistDatabase.getByZoneId(zoneId)).thenReturn(playlistDto);

        final ZonePlaylist playlist = new ZonePlaylist(Arrays.asList(new Song("C"), new Song("D")));
        testSubject.handleZonePlaylistUpdated(new ZonePlaylistUpdated(zoneId, playlist));

        verify(zonePlaylistDatabase).update(new ZonePlaylistDto(zoneId, ZONE_NAME, playlist));
    }

    @Test(expected = InstanceNotFoundException.class)
    public void handleZonePlaylistUpdatedForUnkownZone() throws Exception {
        final UUID zoneId = UUID.randomUUID();

        when(zonePlaylistDatabase.getByZoneId(zoneId)).thenReturn(Optional.empty());

        testSubject.handleZonePlaylistUpdated(new ZonePlaylistUpdated(zoneId, new ZonePlaylist(Arrays.asList(new Song("song 1"), new Song("song 2")))));
    }
}