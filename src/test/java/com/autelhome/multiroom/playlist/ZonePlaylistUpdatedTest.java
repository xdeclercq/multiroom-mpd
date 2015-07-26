package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.song.Song;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;

public class ZonePlaylistUpdatedTest {

    private static final Song SONG_A = new Song("Song A");
    private static final Song SONG_B = new Song("Song B");
    private static final Song SONG_C = new Song("Song C");

    @Test
    public void getNewPlayerStatus() throws Exception {
        final ZonePlaylist expected = new ZonePlaylist(Arrays.asList(SONG_A, SONG_B));
        final ZonePlaylistUpdated zonePlaylistUpdated = new ZonePlaylistUpdated(UUID.randomUUID(), expected);
        final ZonePlaylist actual = zonePlaylistUpdated.getNewPlaylist();
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void shouldBeEqual() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final ZonePlaylistUpdated zonePlaylistUpdated1 = new ZonePlaylistUpdated(zoneId, new ZonePlaylist(Arrays.asList(SONG_A, SONG_B)));
        final ZonePlaylistUpdated zonePlaylistUpdated2 = new ZonePlaylistUpdated(zoneId, new ZonePlaylist(Arrays.asList(SONG_A, SONG_B)));

        assertThat(zonePlaylistUpdated1).isEqualTo(zonePlaylistUpdated1);
        assertThat(zonePlaylistUpdated1).isEqualTo(zonePlaylistUpdated2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final ZonePlaylistUpdated zonePlaylistUpdated1 = new ZonePlaylistUpdated(zoneId, new ZonePlaylist(Arrays.asList(SONG_A, SONG_B)));
        final ZonePlaylistUpdated zonePlaylistUpdated2 = new ZonePlaylistUpdated(UUID.randomUUID(), new ZonePlaylist(Arrays.asList(SONG_A, SONG_B)));
        final ZonePlaylistUpdated zonePlaylistUpdated3 = new ZonePlaylistUpdated(zoneId, new ZonePlaylist(Arrays.asList(SONG_A, SONG_C)));

        assertNotEquals(zonePlaylistUpdated1, " ");
        assertNotEquals(zonePlaylistUpdated1, null);
        assertThat(zonePlaylistUpdated1).isNotEqualTo(zonePlaylistUpdated2);
        assertThat(zonePlaylistUpdated1).isNotEqualTo(zonePlaylistUpdated3);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final int hashCode1 = new ZonePlaylistUpdated(zoneId, new ZonePlaylist(Arrays.asList(SONG_A, SONG_B))).hashCode();
        final int hashCode2 = new ZonePlaylistUpdated(zoneId, new ZonePlaylist(Arrays.asList(SONG_A, SONG_B))).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final int hashCode1 = new ZonePlaylistUpdated(zoneId, new ZonePlaylist(Arrays.asList(SONG_A, SONG_B))).hashCode();
        final int hashCode2 = new ZonePlaylistUpdated(UUID.randomUUID(), new ZonePlaylist(Arrays.asList(SONG_A, SONG_B))).hashCode();
        final int hashCode3 = new ZonePlaylistUpdated(zoneId, new ZonePlaylist(Arrays.asList(SONG_A, SONG_C))).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
    }

    @Test
    public void toStringShouldContainFields() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final ZonePlaylist newPlaylist = new ZonePlaylist(Arrays.asList(SONG_A, SONG_B));
        final String actual = new ZonePlaylistUpdated(zoneId, newPlaylist).toString();

        assertThat(actual).contains(zoneId.toString());
        assertThat(actual).contains(newPlaylist.toString());
    }

}