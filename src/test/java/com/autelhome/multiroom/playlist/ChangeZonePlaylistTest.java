package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.song.Song;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotEquals;

public class ChangeZonePlaylistTest {

    private static final String SONG_A = "song A";
    private static final String SONG_B = "song B";
    private static final String SONG_C = "song C";

    @Test
    public void shouldBeEqual() throws Exception {
        final UUID id = UUID.randomUUID();
        final ChangeZonePlaylist changeZonePlaylist1 = new ChangeZonePlaylist(id, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))), 2);
        final ChangeZonePlaylist changeZonePlaylist2 = new ChangeZonePlaylist(id, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))), 2);

        assertThat(changeZonePlaylist1).isEqualTo(changeZonePlaylist1);
        assertThat(changeZonePlaylist1).isEqualTo(changeZonePlaylist2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final UUID id = UUID.randomUUID();
        final ChangeZonePlaylist changeZonePlaylist1 = new ChangeZonePlaylist(id, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))), 2);
        final ChangeZonePlaylist changeZonePlaylist2 = new ChangeZonePlaylist(UUID.randomUUID(), new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))), 2);
        final ChangeZonePlaylist changeZonePlaylist3 = new ChangeZonePlaylist(id, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B), new Song(SONG_C))), 2);
        final ChangeZonePlaylist changeZonePlaylist4 = new ChangeZonePlaylist(id, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))), 3);


        assertNotEquals(changeZonePlaylist1, " ");
        assertNotEquals(changeZonePlaylist1, null);
        assertThat(changeZonePlaylist1).isNotEqualTo(changeZonePlaylist2);
        assertThat(changeZonePlaylist1).isNotEqualTo(changeZonePlaylist3);
        assertThat(changeZonePlaylist1).isNotEqualTo(changeZonePlaylist4);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final UUID id = UUID.randomUUID();
        final int hashCode1 = new ChangeZonePlaylist(id, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))), 2).hashCode();
        final int hashCode2 = new ChangeZonePlaylist(id, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))), 2).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldBeDifferent() throws Exception {
        final UUID id = UUID.randomUUID();
        final int hashCode1 = new ChangeZonePlaylist(id, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))), 2).hashCode();
        final int hashCode2 = new ChangeZonePlaylist(UUID.randomUUID(), new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))), 2).hashCode();
        final int hashCode3 = new ChangeZonePlaylist(id, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_C))), 2).hashCode();
        final int hashCode4 = new ChangeZonePlaylist(id, new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B))), 3).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
        assertThat(hashCode1).isNotEqualTo(hashCode4);
    }

    @Test
    public void toStringShouldContainFieldsValue() throws Exception {
        final UUID id = UUID.randomUUID();
        final Collection<Song> songs = Arrays.asList(new Song(SONG_A), new Song(SONG_B));
        final ZonePlaylist newPlaylist = new ZonePlaylist(songs);
        final int originalVersion = 38408352;
        final String actual = new ChangeZonePlaylist(id, newPlaylist,  originalVersion).toString();

        MatcherAssert.assertThat(actual, containsString(id.toString()));
        MatcherAssert.assertThat(actual, containsString(newPlaylist.toString()));
        MatcherAssert.assertThat(actual, containsString(Integer.toString(originalVersion)));
    }

    @Test
    public void getNewPlaylist() throws Exception {
        final ZonePlaylist expected = new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song(SONG_B)));

        final ChangeZonePlaylist changeZonePlaylist = new ChangeZonePlaylist(UUID.randomUUID(), expected, 2);

        final ZonePlaylist actual = changeZonePlaylist.getNewPlaylist();

        assertThat(actual).isEqualTo(expected);
    }

}