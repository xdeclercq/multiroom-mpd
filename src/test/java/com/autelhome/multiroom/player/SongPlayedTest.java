package com.autelhome.multiroom.player;

import com.autelhome.multiroom.song.Song;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SongPlayedTest {
    private static final UUID ZONE_ID = UUID.randomUUID();
    private static final String SONG_A = "Song A";
    private static final String SONG_B = "Song B";

    @Test
    public void getSong() throws Exception {
        final Song expected = new Song(SONG_A);
        final Song actual = new SongPlayed(UUID.randomUUID(), expected).getSong();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final SongPlayed songPlayed1 = new SongPlayed(ZONE_ID, new Song(SONG_A));
        final SongPlayed songPlayed2 = new SongPlayed(ZONE_ID, new Song(SONG_A));

        assertThat(songPlayed1).isEqualTo(songPlayed1);
        assertThat(songPlayed1).isEqualTo(songPlayed2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final SongPlayed songPlayed1 = new SongPlayed(ZONE_ID, new Song(SONG_A));
        final SongPlayed songPlayed2 = new SongPlayed(UUID.randomUUID(), new Song(SONG_A));
        final SongPlayed songPlayed3 = new SongPlayed(ZONE_ID, new Song(SONG_B));

        assertThat(songPlayed1).isNotEqualTo(" ");
        assertThat(songPlayed1).isNotEqualTo(null);
        assertThat(songPlayed1).isNotEqualTo(songPlayed2);
        assertThat(songPlayed1).isNotEqualTo(songPlayed3);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int hashCode1 = new SongPlayed(ZONE_ID, new Song(SONG_A)).hashCode();
        final int hashCode2 = new SongPlayed(ZONE_ID, new Song(SONG_A)).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int hashCode1 = new SongPlayed(ZONE_ID, new Song(SONG_A)).hashCode();
        final int hashCode2 = new SongPlayed(UUID.randomUUID(), new Song(SONG_A)).hashCode();
        final int hashCode3 = new SongPlayed(ZONE_ID, new Song(SONG_B)).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
    }

    @Test
    public void toStringShouldContainFieldsValue() throws Exception {
        final UUID id = UUID.randomUUID();
        final Song song = new Song(SONG_A);
        final String actual = new SongPlayed(id, song).toString();

        assertThat(actual).contains(id.toString());
        assertThat(actual).contains(song.toString());
    }

}