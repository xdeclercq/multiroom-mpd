package com.autelhome.multiroom.player;

import com.autelhome.multiroom.song.Song;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangeCurrentSongTest {

    private static final UUID ZONE_ID = UUID.randomUUID();
    private static final String SONG_A = "Song A";
    private static final String SONG_B = "Song B";

    @Test
    public void getNewCurrentSong() throws Exception {
        final CurrentSong expected = new CurrentSong(new Song(SONG_A), 1);
        final CurrentSong actual = new ChangeCurrentSong(UUID.randomUUID(), expected, 343).getNewCurrentSong();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final int originalVersion = 364734;
        final ChangeCurrentSong changeCurrentSong1 = new ChangeCurrentSong(ZONE_ID, new CurrentSong(new Song(SONG_A), 1), originalVersion);
        final ChangeCurrentSong changeCurrentSong2 = new ChangeCurrentSong(ZONE_ID, new CurrentSong(new Song(SONG_A), 1), originalVersion);

        assertThat(changeCurrentSong1).isEqualTo(changeCurrentSong1);
        assertThat(changeCurrentSong1).isEqualTo(changeCurrentSong2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final int originalVersion = 364734;
        final ChangeCurrentSong changeCurrentSong1 = new ChangeCurrentSong(ZONE_ID, new CurrentSong(new Song(SONG_A), 1), originalVersion);
        final ChangeCurrentSong changeCurrentSong2 = new ChangeCurrentSong(UUID.randomUUID(), new CurrentSong(new Song(SONG_A), 1), originalVersion);
        final ChangeCurrentSong changeCurrentSong3 = new ChangeCurrentSong(ZONE_ID, new CurrentSong(new Song(SONG_A), 2), originalVersion);
        final ChangeCurrentSong changeCurrentSong4 = new ChangeCurrentSong(ZONE_ID, new CurrentSong(new Song(SONG_A), 1), 8323);

        assertThat(changeCurrentSong1).isNotEqualTo(" ");
        assertThat(changeCurrentSong1).isNotEqualTo(null);
        assertThat(changeCurrentSong1).isNotEqualTo(changeCurrentSong2);
        assertThat(changeCurrentSong1).isNotEqualTo(changeCurrentSong3);
        assertThat(changeCurrentSong1).isNotEqualTo(changeCurrentSong4);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int originalVersion = 364734;
        final int hashCode1 = new ChangeCurrentSong(ZONE_ID, new CurrentSong(new Song(SONG_A), 1), originalVersion).hashCode();
        final int hashCode2 = new ChangeCurrentSong(ZONE_ID, new CurrentSong(new Song(SONG_A), 1), originalVersion).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int originalVersion = 364734;
        final int hashCode1 = new ChangeCurrentSong(ZONE_ID, new CurrentSong(new Song(SONG_A), 1), originalVersion).hashCode();
        final int hashCode2 = new ChangeCurrentSong(UUID.randomUUID(), new CurrentSong(new Song(SONG_A), 1), originalVersion).hashCode();
        final int hashCode3 = new ChangeCurrentSong(ZONE_ID, new CurrentSong(new Song(SONG_B), 2), originalVersion).hashCode();
        final int hashCode4 = new ChangeCurrentSong(ZONE_ID, new CurrentSong(new Song(SONG_A), 1), 53847).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
        assertThat(hashCode1).isNotEqualTo(hashCode4);
    }

    @Test
    public void toStringShouldContainFieldsValue() throws Exception {
        final UUID id = UUID.randomUUID();
        final CurrentSong newCurrentSong = new CurrentSong(new Song(SONG_A), 1);
        final int originalVersion = 38408352;
        final String actual = new ChangeCurrentSong(id, newCurrentSong,  originalVersion).toString();

        assertThat(actual).contains(id.toString());
        assertThat(actual).contains(newCurrentSong.toString());
        assertThat(actual).contains(Integer.toString(originalVersion));
    }
}