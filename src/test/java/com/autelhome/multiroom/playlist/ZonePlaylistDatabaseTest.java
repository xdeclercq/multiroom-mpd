package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.util.InstanceAlreadyPresentException;
import com.autelhome.multiroom.util.InstanceNotFoundException;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ZonePlaylistDatabaseTest {

    private static final String A_ZONE = "a zone";
    private static final String SONG_A = "song A";
    private static final String SONG_B = "song B";
    private static final String SONG_C = "song C";
    private final ZonePlaylistDatabase testSubject = new ZonePlaylistDatabase();

    @Test
    public void addUpdateAndGetByZoneName() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final String zoneName = A_ZONE;
        final ZonePlaylist playlist = new ZonePlaylist(Arrays.asList(new PlaylistSong(new Song(SONG_A), 1), new PlaylistSong(new Song(SONG_B), 2)));
        testSubject.add(new ZonePlaylistDto(zoneId, zoneName, playlist));

        final Optional<ZonePlaylistDto> actual1 = testSubject.getByZoneName(zoneName);

        final Optional<ZonePlaylistDto> expected1 = Optional.of(new ZonePlaylistDto(zoneId, zoneName, playlist));
        assertThat(actual1).isEqualTo(expected1);

        final String zoneName2 = "another name";
        final ZonePlaylistDto expected2 = new ZonePlaylistDto(zoneId, zoneName2, new ZonePlaylist(Arrays.asList(new PlaylistSong(new Song(SONG_A), 1), new PlaylistSong(new Song(SONG_C), 2))));
        testSubject.update(expected2);
        final Optional<ZonePlaylistDto> actual2 = testSubject.getByZoneName(zoneName2);
        assertThat(actual2).isEqualTo(Optional.of(expected2));

        final Optional<ZonePlaylistDto> actual3 = testSubject.getByZoneName(zoneName);
        final Optional<ZonePlaylistDto> expected3 = Optional.<ZonePlaylistDto>empty();
        assertThat(actual3).isEqualTo(expected3);
    }

    @Test
    public void addAndGetByZoneId() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final String zoneName = A_ZONE;
        final ZonePlaylist playlist = new ZonePlaylist(Arrays.asList(new PlaylistSong(new Song(SONG_A), 1), new PlaylistSong(new Song(SONG_B), 2)));
        testSubject.add(new ZonePlaylistDto(zoneId, zoneName, playlist));

        final Optional<ZonePlaylistDto> actual = testSubject.getByZoneId(zoneId);

        final Optional<ZonePlaylistDto> expected = Optional.of(new ZonePlaylistDto(zoneId, zoneName, playlist));
        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = InstanceAlreadyPresentException.class)
    public void addAlreadyExistingZoneWithSameId() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final ZonePlaylist playlist = new ZonePlaylist(Arrays.asList(new PlaylistSong(new Song(SONG_A), 1), new PlaylistSong(new Song(SONG_B), 2)));
        testSubject.add(new ZonePlaylistDto(zoneId, A_ZONE, playlist));
        testSubject.add(new ZonePlaylistDto(zoneId, "another zone name", new ZonePlaylist(Arrays.asList(new PlaylistSong(new Song(SONG_A), 1), new PlaylistSong(new Song(SONG_C), 2)))));
    }

    @Test(expected = InstanceAlreadyPresentException.class)
    public void addAlreadyExistingZoneWithSameName() throws Exception {
        final ZonePlaylist playlist = new ZonePlaylist(Arrays.asList(new PlaylistSong(new Song(SONG_A), 1), new PlaylistSong(new Song(SONG_B), 2)));
        testSubject.add(new ZonePlaylistDto(UUID.randomUUID(), A_ZONE, playlist));
        testSubject.add(new ZonePlaylistDto(UUID.randomUUID(), A_ZONE, new ZonePlaylist(Arrays.asList(new PlaylistSong(new Song(SONG_A), 1), new PlaylistSong(new Song(SONG_C), 2)))));
    }

    @Test(expected = InstanceNotFoundException.class)
    public void updateNotFound() throws Exception {
        testSubject.update(new ZonePlaylistDto(UUID.randomUUID(), "some zone", new ZonePlaylist(Arrays.asList(new PlaylistSong(new Song(SONG_A), 1), new PlaylistSong(new Song(SONG_C), 2)))));
    }

}