package com.autelhome.multiroom.song;

import org.bff.javampd.objects.MPDSong;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SongTest {

    private static final String SONG_A = "Song A";
    private static final String SONG_B = "Song B";

    @Test
    public void getTitle() throws Exception {
        final String expected = "song title";
        final String actual = new Song(expected).getTitle();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void fromMPDSong() throws Exception {
        final MPDSong mpdSong = new MPDSong();
        mpdSong.setTitle(SONG_A);
        final Song actual = Song.fromMPDSong(mpdSong);

        final Song expected = new Song(SONG_A);
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void shouldBeEqual() throws Exception {
        final Song song1 = new Song(SONG_A);
        final Song song2 = new Song(SONG_A);

        assertThat(song1).isEqualTo(song1);
        assertThat(song1).isEqualTo(song2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final Song song1 = new Song(SONG_A);
        final Song song2 = new Song(SONG_B);

        assertThat(song1).isNotEqualTo(" ");
        assertThat(song1).isNotEqualTo(null);
        assertThat(song1).isNotEqualTo(song2);
    }


    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int hashCode1 = new Song(SONG_A).hashCode();
        final int hashCode2 = new Song(SONG_A).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int hashCode1 = new Song(SONG_A).hashCode();
        final int hashCode2 = new Song(SONG_B).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
    }

    @Test
    public void toStringShouldContainFieldsValue() throws Exception {
        final String actual = new Song(SONG_A).toString();

        assertThat(actual).contains(SONG_A);
    }

}