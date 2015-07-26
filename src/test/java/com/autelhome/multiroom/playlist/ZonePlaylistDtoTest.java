package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.song.Song;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;

public class ZonePlaylistDtoTest {

    private static final String MY_ZONE = "my zone";
    private static final String SONG_A = "song A";
    private static final String SONG_B = "song B";
    private static final String SONG_C = "song C";


    @Test
    public void getZoneName() throws Exception {
        final String expected = "a zone";
        final ZonePlaylistDto playlist = new ZonePlaylistDto(UUID.randomUUID(), expected, new ZonePlaylist(Arrays.asList(new Song("song 1"), new Song("song 2"))));
        assertThat(playlist.getZoneName()).isEqualTo(expected);
    }

    @Test
    public void getSongs() throws Exception {
        final ZonePlaylist expected = new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B)));
        final ZonePlaylistDto playlist = new ZonePlaylistDto(UUID.randomUUID(), "a zone", expected);
        assertThat(playlist.getSongs()).isEqualTo(Arrays.asList(new Song(SONG_A), new Song(SONG_B)));
    }

    @Test
    public void getZoneId() throws Exception {
        final UUID expected = UUID.randomUUID();
        final ZonePlaylistDto playlist = new ZonePlaylistDto(expected, "a zone", new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))));
        assertThat(playlist.getZoneId()).isEqualTo(expected);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final ZonePlaylistDto zonePlaylistDto1 = new ZonePlaylistDto(zoneId, MY_ZONE, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))));
        final ZonePlaylistDto zonePlaylistDto2 = new ZonePlaylistDto(zoneId, MY_ZONE, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))));

        assertThat(zonePlaylistDto1).isEqualTo(zonePlaylistDto1);
        assertThat(zonePlaylistDto1).isEqualTo(zonePlaylistDto2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final ZonePlaylistDto zonePlaylistDto1 = new ZonePlaylistDto(zoneId, MY_ZONE, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))));
        final ZonePlaylistDto zonePlaylistDto2 = new ZonePlaylistDto(UUID.randomUUID(), MY_ZONE, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))));
        final ZonePlaylistDto zonePlaylistDto3 = new ZonePlaylistDto(zoneId, "another zone", new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))));
        final ZonePlaylistDto zonePlaylistDto4 = new ZonePlaylistDto(zoneId, MY_ZONE, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_C))));

        assertNotEquals(zonePlaylistDto1, " ");
        assertNotEquals(zonePlaylistDto1, null);
        assertThat(zonePlaylistDto1).isNotEqualTo(zonePlaylistDto2);
        assertThat(zonePlaylistDto1).isNotEqualTo(zonePlaylistDto3);
        assertThat(zonePlaylistDto1).isNotEqualTo(zonePlaylistDto4);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final int hashCode1 = new ZonePlaylistDto(zoneId, MY_ZONE, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B)))).hashCode();
        final int hashCode2 = new ZonePlaylistDto(zoneId, MY_ZONE, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B)))).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final int hashCode1 = new ZonePlaylistDto(zoneId, MY_ZONE, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B)))).hashCode();
        final int hashCode2 = new ZonePlaylistDto(UUID.randomUUID(), MY_ZONE, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B)))).hashCode();
        final int hashCode3 = new ZonePlaylistDto(zoneId, "another zone", new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B)))).hashCode();
        final int hashCode4 = new ZonePlaylistDto(zoneId, MY_ZONE, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_C)))).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode2).isNotEqualTo(hashCode3);
        assertThat(hashCode3).isNotEqualTo(hashCode4);
    }

    @Test
    public void toStringShouldContainFields() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final String zoneName = "another zone";
        final ZonePlaylist playlist = new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B)));
        final String actual = new ZonePlaylistDto(zoneId, zoneName, playlist).toString();
        assertThat(actual).contains(zoneId.toString());
        assertThat(actual).contains(zoneName);
        assertThat(actual).contains(playlist.toString());
    }

}