package com.autelhome.multiroom.player;

import org.bff.javampd.Player;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class PlayerStatusTypeTest {



    @Test
    public void parsePlaying() throws Exception {
        final PlayerStatusType expected = PlayerStatusType.PLAYING;

        final PlayerStatusType actual = PlayerStatusType.parse(Player.Status.STATUS_PLAYING);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void parsePaused() throws Exception {
        final PlayerStatusType expected = PlayerStatusType.STOPPED;

        final PlayerStatusType actual = PlayerStatusType.parse(Player.Status.STATUS_PAUSED);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void parseStopped() throws Exception {
        final PlayerStatusType expected = PlayerStatusType.STOPPED;

        final PlayerStatusType actual = PlayerStatusType.parse(Player.Status.STATUS_STOPPED);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void values() throws Exception {
        final  PlayerStatusType[] actual = PlayerStatusType.values();

        final PlayerStatusType[] expected = {PlayerStatusType.PLAYING, PlayerStatusType.STOPPED};

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void valueOfPlaying() throws Exception {
        final  PlayerStatusType actual = PlayerStatusType.valueOf("PLAYING");

        final PlayerStatusType expected = PlayerStatusType.PLAYING;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void valueOfStopped() throws Exception {
        final  PlayerStatusType actual = PlayerStatusType.valueOf("STOPPED");

        final PlayerStatusType expected = PlayerStatusType.STOPPED;

        assertThat(actual).isEqualTo(expected);
    }

}