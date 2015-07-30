package com.autelhome.multiroom.player;

import com.autelhome.multiroom.song.Song;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrentSongUpdatedTest {

    private static final UUID ZONE_ID = UUID.randomUUID();
    private static final String SONG_A = "Song A";
    private static final String SONG_B = "Song B";

    @Test
    public void getNewCurrentSong() throws Exception {
        final Song expected = new Song(SONG_A);
        final Song actual = new CurrentSongUpdated(UUID.randomUUID(), expected).getNewCurrentSong();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final CurrentSongUpdated currentSongUpdated1 = new CurrentSongUpdated(ZONE_ID, new Song(SONG_A));
        final CurrentSongUpdated currentSongUpdated2 = new CurrentSongUpdated(ZONE_ID, new Song(SONG_A));

        assertThat(currentSongUpdated1).isEqualTo(currentSongUpdated1);
        assertThat(currentSongUpdated1).isEqualTo(currentSongUpdated2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final CurrentSongUpdated currentSongUpdated1 = new CurrentSongUpdated(ZONE_ID, new Song(SONG_A));
        final CurrentSongUpdated currentSongUpdated2 = new CurrentSongUpdated(UUID.randomUUID(), new Song(SONG_A));
        final CurrentSongUpdated currentSongUpdated3 = new CurrentSongUpdated(ZONE_ID, new Song(SONG_B));

        assertThat(currentSongUpdated1).isNotEqualTo(" ");
        assertThat(currentSongUpdated1).isNotEqualTo(null);
        assertThat(currentSongUpdated1).isNotEqualTo(currentSongUpdated2);
        assertThat(currentSongUpdated1).isNotEqualTo(currentSongUpdated3);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int hashCode1 = new CurrentSongUpdated(ZONE_ID, new Song(SONG_A)).hashCode();
        final int hashCode2 = new CurrentSongUpdated(ZONE_ID, new Song(SONG_A)).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int hashCode1 = new CurrentSongUpdated(ZONE_ID, new Song(SONG_A)).hashCode();
        final int hashCode2 = new CurrentSongUpdated(UUID.randomUUID(), new Song(SONG_A)).hashCode();
        final int hashCode3 = new CurrentSongUpdated(ZONE_ID, new Song(SONG_B)).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
    }

    @Test
    public void toStringShouldContainFieldsValue() throws Exception {
        final UUID id = UUID.randomUUID();
        final Song newCurrentSong = new Song(SONG_A);
        final String actual = new CurrentSongUpdated(id, newCurrentSong).toString();

        assertThat(actual).contains(id.toString());
        assertThat(actual).contains(newCurrentSong.toString());
    }
}