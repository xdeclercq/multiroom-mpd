package com.autelhome.multiroom.player;

import com.autelhome.multiroom.song.Song;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

public class PlaySongTest {

    private static final UUID ZONE_ID = UUID.randomUUID();
    private static final String SONG_A = "Song A";
    private static final String SONG_B = "Song B";

    @Test
    public void getSong() throws Exception {
        final Song expected = new Song(SONG_A);
        final Song actual = new PlaySong(UUID.randomUUID(), expected, 343).getSong();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final int originalVersion = 364734;
        final PlaySong playSong1 = new PlaySong(ZONE_ID, new Song(SONG_A), originalVersion);
        final PlaySong playSong2 = new PlaySong(ZONE_ID, new Song(SONG_A), originalVersion);

        assertThat(playSong1).isEqualTo(playSong1);
        assertThat(playSong1).isEqualTo(playSong2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final int originalVersion = 364734;
        final PlaySong playSong1 = new PlaySong(ZONE_ID, new Song(SONG_A), originalVersion);
        final PlaySong playSong2 = new PlaySong(UUID.randomUUID(), new Song(SONG_A), originalVersion);
        final PlaySong playSong3 = new PlaySong(ZONE_ID, new Song(SONG_B), originalVersion);
        final PlaySong playSong4 = new PlaySong(ZONE_ID, new Song(SONG_A), 8323);

        assertThat(playSong1).isNotEqualTo(" ");
        assertThat(playSong1).isNotEqualTo(null);
        assertThat(playSong1).isNotEqualTo(playSong2);
        assertThat(playSong1).isNotEqualTo(playSong3);
        assertThat(playSong1).isNotEqualTo(playSong4);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int originalVersion = 364734;
        final int hashCode1 = new PlaySong(ZONE_ID, new Song(SONG_A), originalVersion).hashCode();
        final int hashCode2 = new PlaySong(ZONE_ID, new Song(SONG_A), originalVersion).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int originalVersion = 364734;
        final int hashCode1 = new PlaySong(ZONE_ID, new Song(SONG_A), originalVersion).hashCode();
        final int hashCode2 = new PlaySong(UUID.randomUUID(), new Song(SONG_A), originalVersion).hashCode();
        final int hashCode3 = new PlaySong(ZONE_ID, new Song(SONG_B), originalVersion).hashCode();
        final int hashCode4 = new PlaySong(ZONE_ID, new Song(SONG_A), 53847).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
        assertThat(hashCode1).isNotEqualTo(hashCode4);
    }

    @Test
    public void toStringShouldContainFieldsValue() throws Exception {
        final UUID id = UUID.randomUUID();
        final Song song = new Song(SONG_A);
        final int originalVersion = 38408352;
        final String actual = new PlaySong(id, song, originalVersion).toString();

        MatcherAssert.assertThat(actual, containsString(id.toString()));
        MatcherAssert.assertThat(actual, containsString(song.toString()));
        MatcherAssert.assertThat(actual, containsString(Integer.toString(originalVersion)));
    }
}