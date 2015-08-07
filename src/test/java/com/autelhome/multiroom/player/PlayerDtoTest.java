package com.autelhome.multiroom.player;

import com.autelhome.multiroom.song.Song;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerDtoTest {

    private static final String MY_ZONE = "my zone";
    private static final String SONG_A = "Song A";
    private static final String SONG_B = "Song B";

    @Test
    public void getZoneName() throws Exception {
        final String expected = "a zone";
        final PlayerDto player = new PlayerDto(UUID.randomUUID(), expected, PlayerStatus.PAUSED);
        assertThat(player.getZoneName()).isEqualTo(expected);
    }

    @Test
    public void getStatus() throws Exception {
        final PlayerStatus expected = PlayerStatus.PAUSED;
        final PlayerDto player = new PlayerDto(UUID.randomUUID(), "a zone", expected);
        assertThat(player.getStatus()).isEqualTo(expected);
    }

    @Test
    public void getZoneId() throws Exception {
        final UUID expected = UUID.randomUUID();
        final PlayerDto player = new PlayerDto(expected, "a zone", PlayerStatus.PAUSED);
        assertThat(player.getZoneId()).isEqualTo(expected);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final int hashCode1 = new PlayerDto(zoneId, MY_ZONE, PlayerStatus.PAUSED, new CurrentSong(new Song(SONG_A), 1)).hashCode();
        final int hashCode2 = new PlayerDto(zoneId, MY_ZONE, PlayerStatus.PAUSED, new CurrentSong(new Song(SONG_A), 1)).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final int hashCode1 = new PlayerDto(zoneId, MY_ZONE, PlayerStatus.PAUSED, new CurrentSong(new Song(SONG_A), 1)).hashCode();
        final int hashCode2 = new PlayerDto(UUID.randomUUID(), MY_ZONE, PlayerStatus.PAUSED, new CurrentSong(new Song(SONG_A), 1)).hashCode();
        final int hashCode3 = new PlayerDto(zoneId, "antoher zone", PlayerStatus.PAUSED, new CurrentSong(new Song(SONG_A), 1)).hashCode();
        final int hashCode4 = new PlayerDto(zoneId, MY_ZONE, PlayerStatus.PLAYING, new CurrentSong(new Song(SONG_A), 1)).hashCode();
        final int hashCode5 = new PlayerDto(zoneId, MY_ZONE, PlayerStatus.PAUSED, new CurrentSong(new Song(SONG_B), 2)).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
        assertThat(hashCode1).isNotEqualTo(hashCode4);
        assertThat(hashCode1).isNotEqualTo(hashCode5);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final PlayerDto player1 = new PlayerDto(zoneId, MY_ZONE, PlayerStatus.PAUSED, new CurrentSong(new Song(SONG_A), 1));
        final PlayerDto player2 = new PlayerDto(zoneId, MY_ZONE, PlayerStatus.PAUSED, new CurrentSong(new Song(SONG_A), 1));

        assertThat(player1).isEqualTo(player1);
        assertThat(player1).isEqualTo(player2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final PlayerDto player1 = new PlayerDto(zoneId, MY_ZONE, PlayerStatus.PAUSED, new CurrentSong(new Song(SONG_A), 1));
        final PlayerDto player2 = new PlayerDto(UUID.randomUUID(), MY_ZONE, PlayerStatus.PAUSED, new CurrentSong(new Song(SONG_A), 1));
        final PlayerDto player3 = new PlayerDto(zoneId, "antoher zone", PlayerStatus.PAUSED, new CurrentSong(new Song(SONG_A), 1));
        final PlayerDto player4 = new PlayerDto(zoneId, MY_ZONE, PlayerStatus.PLAYING, new CurrentSong(new Song(SONG_A), 1));
        final PlayerDto player5 = new PlayerDto(zoneId, MY_ZONE, PlayerStatus.PAUSED, new CurrentSong(new Song(SONG_B), 1));

        assertThat(player1).isNotEqualTo(" ");
        assertThat(player1).isNotEqualTo(null);
        assertThat(player1).isNotEqualTo(player2);
        assertThat(player1).isNotEqualTo(player3);
        assertThat(player1).isNotEqualTo(player4);
        assertThat(player1).isNotEqualTo(player5);
    }

    @Test
    public void toStringShouldContainFields() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final String zoneName = "another zone";
        final PlayerStatus status = PlayerStatus.PAUSED;
        final CurrentSong currentSong = new CurrentSong(new Song(SONG_A), 1);
        final String actual = new PlayerDto(zoneId, zoneName, status, currentSong).toString();
        assertThat(actual).contains(zoneId.toString());
        assertThat(actual).contains(zoneName);
        assertThat(actual).contains(status.toString());
        assertThat(actual).contains(currentSong.toString());
    }
}