package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.song.Song;
import org.bff.javampd.objects.MPDSong;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlaylistSongTest {

    private static final String SONG_A = "Song A";
    private static final String SONG_B = "Song B";

    @Test
    public void fromMPDSong() throws Exception {
        final PlaylistSong expected = new PlaylistSong(new Song(SONG_A), 2);

        final MPDSong mpdSongA = new MPDSong();
        mpdSongA.setTitle(SONG_A);
        mpdSongA.setPosition(1);
        final PlaylistSong actual = PlaylistSong.fromMPDSong(mpdSongA);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getSong() throws Exception {
        final Song expected = new Song(SONG_A);
        final Song actual = new PlaylistSong(new Song(SONG_A), 1).getSong();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getPosition() throws Exception {
        final int expected = 87;
        final int actual = new PlaylistSong(new Song(SONG_A), 87).getPosition();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final PlaylistSong playlistSong1 = new PlaylistSong(new Song(SONG_A), 1);
        final PlaylistSong playlistSong2 = new PlaylistSong(new Song(SONG_A), 1);

        assertThat(playlistSong1).isEqualTo(playlistSong1);
        assertThat(playlistSong1).isEqualTo(playlistSong2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final PlaylistSong playlistSong1 = new PlaylistSong(new Song(SONG_A), 1);
        final PlaylistSong playlistSong2 = new PlaylistSong(new Song(SONG_B), 1);
        final PlaylistSong playlistSong3 = new PlaylistSong(new Song(SONG_A), 2);

        assertThat(playlistSong1).isNotEqualTo(null);
        assertThat(playlistSong1).isNotEqualTo(" ");
        assertThat(playlistSong1).isNotEqualTo(playlistSong2);
        assertThat(playlistSong1).isNotEqualTo(playlistSong3);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int hashCode1 = new PlaylistSong(new Song(SONG_A), 1).hashCode();
        final int hashCode2 = new PlaylistSong(new Song(SONG_A), 1).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int hashCode1 = new PlaylistSong(new Song(SONG_A), 1).hashCode();
        final int hashCode2 = new PlaylistSong(new Song(SONG_B), 1).hashCode();
        final int hashCode3 = new PlaylistSong(new Song(SONG_A), 2).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
    }

    @Test
    public void toStringShouldContainFieldsValue() throws Exception {
        final Song song = new Song(SONG_A);
        final int position = 932;
        final String actual = new PlaylistSong(song, position).toString();

        assertThat(actual).contains(song.toString());
        assertThat(actual).contains(String.valueOf(position));
    }

}