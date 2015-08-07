package com.autelhome.multiroom.player;

import com.autelhome.multiroom.song.Song;
import org.bff.javampd.objects.MPDSong;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrentSongTest {

    private static final String SONG_A = "Song A";
    private static final String SONG_B = "Song B";

    @Test
    public void fromMPDSong() throws Exception {
        final CurrentSong expected = new CurrentSong(new Song(SONG_A), 2);

        final MPDSong mpdSongA = new MPDSong();
        mpdSongA.setTitle(SONG_A);
        mpdSongA.setPosition(1);
        final CurrentSong actual = CurrentSong.fromMPDSong(mpdSongA);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getSong() throws Exception {
        final Song expected = new Song(SONG_A);
        final Song actual = new CurrentSong(new Song(SONG_A), 1).getSong();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getPosition() throws Exception {
        final int expected = 87;
        final int actual = new CurrentSong(new Song(SONG_A), 87).getPosition();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final CurrentSong currentSong1 = new CurrentSong(new Song(SONG_A), 1);
        final CurrentSong currentSong2 = new CurrentSong(new Song(SONG_A), 1);

        assertThat(currentSong1).isEqualTo(currentSong1);
        assertThat(currentSong1).isEqualTo(currentSong2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final CurrentSong currentSong1 = new CurrentSong(new Song(SONG_A), 1);
        final CurrentSong currentSong2 = new CurrentSong(new Song(SONG_B), 1);
        final CurrentSong currentSong3 = new CurrentSong(new Song(SONG_A), 2);

        assertThat(currentSong1).isNotEqualTo(null);
        assertThat(currentSong1).isNotEqualTo(" ");
        assertThat(currentSong1).isNotEqualTo(currentSong2);
        assertThat(currentSong1).isNotEqualTo(currentSong3);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int hashCode1 = new CurrentSong(new Song(SONG_A), 1).hashCode();
        final int hashCode2 = new CurrentSong(new Song(SONG_A), 1).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int hashCode1 = new CurrentSong(new Song(SONG_A), 1).hashCode();
        final int hashCode2 = new CurrentSong(new Song(SONG_B), 1).hashCode();
        final int hashCode3 = new CurrentSong(new Song(SONG_A), 2).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
    }

    @Test
    public void toStringShouldContainFieldsValue() throws Exception {
        final Song song = new Song(SONG_A);
        final int position = 932;
        final String actual = new CurrentSong(song, position).toString();

        assertThat(actual).contains(song.toString());
        assertThat(actual).contains(String.valueOf(position));
    }

}