package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.zone.ZoneDto;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZonePlaylistResourceFactoryTest {

    private final ZonePlaylistService zonePlaylistService = mock(ZonePlaylistService.class);
    private final ZonePlaylistRepresentationFactory zonePlaylistRepresentationFactory = mock(ZonePlaylistRepresentationFactory.class);
    private final ZonePlaylistResourceFactory testSubject = new ZonePlaylistResourceFactory(zonePlaylistService, zonePlaylistRepresentationFactory);

    @Test
    public void newInstance() throws Exception {
        final String zoneName = "Kitchen";
        final UUID zoneId = UUID.randomUUID();
        final ZoneDto kitchen = new ZoneDto(zoneId, zoneName, 7912, 1);

        when(zonePlaylistService.getPlaylistByZoneName(zoneName)).thenReturn(Optional.of(new ZonePlaylistDto(zoneId, zoneName, new ZonePlaylist(Arrays.asList(new Song("Song A"), new Song("Song B"))))));
        final ZonePlaylistResource expected = new ZonePlaylistResource(kitchen, zonePlaylistService, zonePlaylistRepresentationFactory);
        final ZonePlaylistResource actual = testSubject.newInstance(kitchen);

        assertThat(actual).isEqualTo(expected);
    }
}