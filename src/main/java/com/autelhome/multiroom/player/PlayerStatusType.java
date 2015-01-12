package com.autelhome.multiroom.player;

import org.bff.javampd.Player;

/**
 * Represents the player status.
 *
 * @author xavier
 */
public enum PlayerStatusType
{
    PLAYING, STOPPED;

    public static PlayerStatusType parse(final Player.Status status)
    {
        if (status == Player.Status.STATUS_PLAYING) {
            return PLAYING;
        }
        return STOPPED;
    }
}
