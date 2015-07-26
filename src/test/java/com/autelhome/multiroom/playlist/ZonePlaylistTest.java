package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.song.Song;
import org.bff.javampd.Playlist;
import org.bff.javampd.objects.MPDSong;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;

public class ZonePlaylistTest {

    private static final Song SONG_A = new Song("Song A");
    private static final Song SONG_B = new Song("Song B");
    private static final Song SONG_C = new Song("Song C");

    @Test
    public void getSongs() throws Exception {
        final List<Song> expected = Arrays.asList(SONG_A, SONG_B);
        final ZonePlaylist testSubject = new ZonePlaylist(expected);
        final Collection<Song> actual = testSubject.getSongs();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void fromMPDPlaylist() throws Exception {
        final Playlist mpdPlaylist = mock(Playlist.class);
        final MPDSong mpdSong1 = new MPDSong();
        mpdSong1.setTitle("Song 1");
        final MPDSong mpdSong2 = new MPDSong();
        mpdSong2.setTitle("Song 2");
        Mockito.when(mpdPlaylist.getSongList()).thenReturn(Arrays.asList(mpdSong1, mpdSong2));

        final ZonePlaylist actual = ZonePlaylist.fromMPDPlaylist(mpdPlaylist);

        final ZonePlaylist expected = new ZonePlaylist(Arrays.asList(new Song("Song 1"), new Song("Song 2")));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final ZonePlaylist zonePlaylist1 = new ZonePlaylist(Arrays.asList(SONG_A, SONG_B));
        final ZonePlaylist zonePlaylist2 = new ZonePlaylist(Arrays.asList(SONG_A, SONG_B));

        assertThat(zonePlaylist1).isEqualTo(zonePlaylist1);
        assertThat(zonePlaylist1).isEqualTo(zonePlaylist2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final ZonePlaylist zonePlaylist1 = new ZonePlaylist(Arrays.asList(SONG_A, SONG_B));
        final ZonePlaylist zonePlaylist2 = new ZonePlaylist(Arrays.asList(SONG_A, SONG_C));

        assertNotEquals(zonePlaylist1, " ");
        assertNotEquals(zonePlaylist1, null);
        assertThat(zonePlaylist1).isNotEqualTo(zonePlaylist2);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int hashCode1 = new ZonePlaylist(Arrays.asList(SONG_A, SONG_B)).hashCode();
        final int hashCode2 = new ZonePlaylist(Arrays.asList(SONG_A, SONG_B)).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int hashCode1 = new ZonePlaylist(Arrays.asList(SONG_A, SONG_B)).hashCode();
        final int hashCode2 = new ZonePlaylist(Arrays.asList(SONG_A, SONG_C)).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
    }

}