package com.autelhome.multiroom.player;

import org.junit.Test;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;

public class PlayerStatusUpdatedTest {

    @Test
    public void getNewPlayerStatus() throws Exception {
        final PlayerStatus expected = PlayerStatus.PAUSED;
        final PlayerStatusUpdated playerStatusUpdated = new PlayerStatusUpdated(UUID.randomUUID(), expected);
        final PlayerStatus actual = playerStatusUpdated.getNewPlayerStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final PlayerStatusUpdated playerStatusUpdated1 = new PlayerStatusUpdated(zoneId, PlayerStatus.PAUSED);
        final PlayerStatusUpdated playerStatusUpdated2 = new PlayerStatusUpdated(zoneId, PlayerStatus.PAUSED);

        assertThat(playerStatusUpdated1).isEqualTo(playerStatusUpdated1);
        assertThat(playerStatusUpdated1).isEqualTo(playerStatusUpdated2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final PlayerStatusUpdated playerStatusUpdated1 = new PlayerStatusUpdated(zoneId, PlayerStatus.PAUSED);
        final PlayerStatusUpdated playerStatusUpdated2 = new PlayerStatusUpdated(UUID.randomUUID(), PlayerStatus.PAUSED);
        final PlayerStatusUpdated playerStatusUpdated3 = new PlayerStatusUpdated(zoneId, PlayerStatus.PLAYING);

        assertNotEquals(playerStatusUpdated1, " ");
        assertNotEquals(playerStatusUpdated1, null);
        assertThat(playerStatusUpdated1).isNotEqualTo(playerStatusUpdated2);
        assertThat(playerStatusUpdated1).isNotEqualTo(playerStatusUpdated3);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final int hashCode1 = new PlayerStatusUpdated(zoneId, PlayerStatus.PAUSED).hashCode();
        final int hashCode2 = new PlayerStatusUpdated(zoneId, PlayerStatus.PAUSED).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final int hashCode1 = new PlayerStatusUpdated(zoneId, PlayerStatus.PAUSED).hashCode();
        final int hashCode2 = new PlayerStatusUpdated(UUID.randomUUID(), PlayerStatus.PAUSED).hashCode();
        final int hashCode3 = new PlayerStatusUpdated(zoneId, PlayerStatus.PLAYING).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
    }

    @Test
    public void toStringShouldContainFields() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final PlayerStatus playerStatus = PlayerStatus.PLAYING;
        final String actual = new PlayerStatusUpdated(zoneId, playerStatus).toString();

        assertThat(actual).contains(zoneId.toString());
        assertThat(actual).contains(playerStatus.toString());
    }
}