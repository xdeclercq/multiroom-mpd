package com.autelhome.multiroom.player;

import org.bff.javampd.Player;
import org.bff.javampd.events.PlayerBasicChangeEvent;

/**
 * Represents the player status.
 *
 * @author xavier
 */
public enum PlayerStatus {
    PLAYING, PAUSED, STOPPED, UNKNOWN;

    /**
     * Returns an PlayerStatus from an {@link Player.Status} instance.
     *
     * @param status an {@link Player.Status} instance
     * @return the equivalent player status
     */
    public static PlayerStatus fromMPDPlayerStatus(final Player.Status status) {
        if (status == Player.Status.STATUS_PLAYING) {
            return PLAYING;
        }
        if (status == Player.Status.STATUS_PAUSED) {
            return PAUSED;
        }
        return STOPPED;
    }

    /**
     * Returns an PlayerStatus from an {@link PlayerBasicChangeEvent.Status} instance.
     *
     * @param status an {@link PlayerBasicChangeEvent.Status} instance
     * @return the equivalent player status
     */
    public static PlayerStatus fromMPDChangeEventStatus(final PlayerBasicChangeEvent.Status status) {
        if (status == PlayerBasicChangeEvent.Status.PLAYER_STARTED || status == PlayerBasicChangeEvent.Status.PLAYER_UNPAUSED) {
            return PLAYING;
        }
        if (status == PlayerBasicChangeEvent.Status.PLAYER_PAUSED) {
            return PAUSED;
        }
        return STOPPED;
    }
}
