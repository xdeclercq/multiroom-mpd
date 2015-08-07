package com.autelhome.multiroom.player;

import com.autelhome.multiroom.song.Song;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SongAtPositionPlayedTest {
    private static final UUID ZONE_ID = UUID.randomUUID();
    private static final String SONG_A = "Song A";
    private static final String SONG_B = "Song B";

    @Test
    public void getCurrentSong() throws Exception {
        final CurrentSong expected = new CurrentSong(new Song(SONG_A), 6);
        final CurrentSong actual = new SongAtPositionPlayed(UUID.randomUUID(), expected).getCurrentSong();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final SongAtPositionPlayed songAtPositionPlayed1 = new SongAtPositionPlayed(ZONE_ID, new CurrentSong(new Song(SONG_A), 1));
        final SongAtPositionPlayed songAtPositionPlayed2 = new SongAtPositionPlayed(ZONE_ID, new CurrentSong(new Song(SONG_A), 1));

        assertThat(songAtPositionPlayed1).isEqualTo(songAtPositionPlayed1);
        assertThat(songAtPositionPlayed1).isEqualTo(songAtPositionPlayed2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final SongAtPositionPlayed songAtPositionPlayed1 = new SongAtPositionPlayed(ZONE_ID, new CurrentSong(new Song(SONG_A), 1));
        final SongAtPositionPlayed songAtPositionPlayed2 = new SongAtPositionPlayed(UUID.randomUUID(), new CurrentSong(new Song(SONG_A), 1));
        final SongAtPositionPlayed songAtPositionPlayed3 = new SongAtPositionPlayed(ZONE_ID, new CurrentSong(new Song(SONG_B), 2));

        assertThat(songAtPositionPlayed1).isNotEqualTo(" ");
        assertThat(songAtPositionPlayed1).isNotEqualTo(null);
        assertThat(songAtPositionPlayed1).isNotEqualTo(songAtPositionPlayed2);
        assertThat(songAtPositionPlayed1).isNotEqualTo(songAtPositionPlayed3);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int hashCode1 = new SongAtPositionPlayed(ZONE_ID, new CurrentSong(new Song(SONG_A), 1)).hashCode();
        final int hashCode2 = new SongAtPositionPlayed(ZONE_ID, new CurrentSong(new Song(SONG_A), 1)).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int hashCode1 = new SongAtPositionPlayed(ZONE_ID, new CurrentSong(new Song(SONG_A), 1)).hashCode();
        final int hashCode2 = new SongAtPositionPlayed(UUID.randomUUID(), new CurrentSong(new Song(SONG_A), 1)).hashCode();
        final int hashCode3 = new SongAtPositionPlayed(ZONE_ID, new CurrentSong(new Song(SONG_B), 2)).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
    }

    @Test
    public void toStringShouldContainFieldsValue() throws Exception {
        final UUID id = UUID.randomUUID();
        final CurrentSong currentSong = new CurrentSong(new Song(SONG_A), 4);
        final String actual = new SongAtPositionPlayed(id, currentSong).toString();

        assertThat(actual).contains(id.toString());
        assertThat(actual).contains(currentSong.toString());
    }

}