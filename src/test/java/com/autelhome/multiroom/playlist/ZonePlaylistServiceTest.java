package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.song.Song;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZonePlaylistServiceTest {
    private final ZonePlaylistDatabase zonePlaylistDatabase = mock(ZonePlaylistDatabase.class);
    private final ZonePlaylistService testSubject = new ZonePlaylistService(zonePlaylistDatabase);

    @Test
    public void getPlayerByZoneName() throws Exception {
        final String zoneName = "some zone";

        final Optional<ZonePlaylistDto> expected = Optional.of(new ZonePlaylistDto(UUID.randomUUID(), zoneName, new ZonePlaylist(Arrays.asList(new Song("Song A"), new Song("Song B")))));
        when(zonePlaylistDatabase.getByZoneName(zoneName)).thenReturn(expected);
        final Optional<ZonePlaylistDto> actual = testSubject.getPlaylistByZoneName(zoneName);

        assertThat(actual).isEqualTo(expected);
    }
}