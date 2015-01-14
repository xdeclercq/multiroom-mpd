package com.autelhome.multiroom.player;

import org.bff.javampd.Player;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class PlayerStatusTest {



    @Test
    public void fromMPDPlayerStatusPlaying() throws Exception {
        final PlayerStatus expected = PlayerStatus.PLAYING;

        final PlayerStatus actual = PlayerStatus.fromMPDPlayerStatus(Player.Status.STATUS_PLAYING);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void fromMPDPlayerStatusPaused() throws Exception {
        final PlayerStatus expected = PlayerStatus.STOPPED;

        final PlayerStatus actual = PlayerStatus.fromMPDPlayerStatus(Player.Status.STATUS_PAUSED);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void fromMPDPlayerStatusStopped() throws Exception {
        final PlayerStatus expected = PlayerStatus.STOPPED;

        final PlayerStatus actual = PlayerStatus.fromMPDPlayerStatus(Player.Status.STATUS_STOPPED);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void values() throws Exception {
        final  PlayerStatus[] actual = PlayerStatus.values();

        final PlayerStatus[] expected = {PlayerStatus.PLAYING, PlayerStatus.STOPPED};

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void valueOfPlaying() throws Exception {
        final PlayerStatus actual = PlayerStatus.valueOf("PLAYING");

        final PlayerStatus expected = PlayerStatus.PLAYING;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void valueOfStopped() throws Exception {
        final PlayerStatus actual = PlayerStatus.valueOf("STOPPED");

        final PlayerStatus expected = PlayerStatus.STOPPED;

        assertThat(actual).isEqualTo(expected);
    }

}