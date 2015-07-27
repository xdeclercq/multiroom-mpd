package com.autelhome.multiroom.player;

import com.autelhome.multiroom.song.Song;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

public class ChangeCurrentSongTest {

    private static final UUID ZONE_ID = UUID.randomUUID();
    private static final String SONG_A = "Song A";
    private static final String SONG_B = "Song B";

    @Test
    public void getNewCurrentSong() throws Exception {
        final Song expected = new Song(SONG_A);
        final Song actual = new ChangeCurrentSong(UUID.randomUUID(), expected, 343).getNewCurrentSong();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final int originalVersion = 364734;
        final ChangeCurrentSong changeCurrentSong1 = new ChangeCurrentSong(ZONE_ID, new Song(SONG_A), originalVersion);
        final ChangeCurrentSong changeCurrentSong2 = new ChangeCurrentSong(ZONE_ID, new Song(SONG_A), originalVersion);

        assertThat(changeCurrentSong1).isEqualTo(changeCurrentSong1);
        assertThat(changeCurrentSong1).isEqualTo(changeCurrentSong2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final int originalVersion = 364734;
        final ChangeCurrentSong changeCurrentSong1 = new ChangeCurrentSong(ZONE_ID, new Song(SONG_A), originalVersion);
        final ChangeCurrentSong changeCurrentSong2 = new ChangeCurrentSong(UUID.randomUUID(), new Song(SONG_A), originalVersion);
        final ChangeCurrentSong changeCurrentSong3 = new ChangeCurrentSong(ZONE_ID, new Song(SONG_B), originalVersion);
        final ChangeCurrentSong changeCurrentSong4 = new ChangeCurrentSong(ZONE_ID, new Song(SONG_A), 8323);

        assertThat(changeCurrentSong1).isNotEqualTo(" ");
        assertThat(changeCurrentSong1).isNotEqualTo(null);
        assertThat(changeCurrentSong1).isNotEqualTo(changeCurrentSong2);
        assertThat(changeCurrentSong1).isNotEqualTo(changeCurrentSong3);
        assertThat(changeCurrentSong1).isNotEqualTo(changeCurrentSong4);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int originalVersion = 364734;
        final int hashCode1 = new ChangeCurrentSong(ZONE_ID, new Song(SONG_A), originalVersion).hashCode();
        final int hashCode2 = new ChangeCurrentSong(ZONE_ID, new Song(SONG_A), originalVersion).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int originalVersion = 364734;
        final int hashCode1 = new ChangeCurrentSong(ZONE_ID, new Song(SONG_A), originalVersion).hashCode();
        final int hashCode2 = new ChangeCurrentSong(UUID.randomUUID(), new Song(SONG_A), originalVersion).hashCode();
        final int hashCode3 = new ChangeCurrentSong(ZONE_ID, new Song(SONG_B), originalVersion).hashCode();
        final int hashCode4 = new ChangeCurrentSong(ZONE_ID, new Song(SONG_A), 53847).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
        assertThat(hashCode1).isNotEqualTo(hashCode4);
    }

    @Test
    public void toStringShouldContainFieldsValue() throws Exception {
        final UUID id = UUID.randomUUID();
        final Song newCurrentSong = new Song(SONG_A);
        final int originalVersion = 38408352;
        final String actual = new ChangeCurrentSong(id, newCurrentSong,  originalVersion).toString();

        MatcherAssert.assertThat(actual, containsString(id.toString()));
        MatcherAssert.assertThat(actual, containsString(newCurrentSong.toString()));
        MatcherAssert.assertThat(actual, containsString(Integer.toString(originalVersion)));
    }
}