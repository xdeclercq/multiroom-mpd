package com.autelhome.multiroom.player;

import org.bff.javampd.Player;

/**
 * Represents the player status.
 *
 * @author xavier
 */
public enum PlayerStatus
{
    PLAYING, STOPPED;

    public static PlayerStatus fromMPDPlayerStatus(final Player.Status status)
    {
        if (status == Player.Status.STATUS_PLAYING) {
            return PLAYING;
        }
        return STOPPED;
    }
}
