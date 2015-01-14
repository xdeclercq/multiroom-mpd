package com.autelhome.multiroom.player;

import com.autelhome.multiroom.zone.Zone;

/**
 * Represents a player linked to a zone.
 *
 * @author xdeclercq
 */
public class Player {

    private final Zone zone;
    private final PlayerStatus status;

    /**
     * Constructor.
     *
     * @param zone a {@link Zone}
     * @param status the initial {@link PlayerStatus}
     */
    public Player(final Zone zone, final PlayerStatus status) {
        this.zone = zone;
        this.status = status;
    }

    public Zone getZone() {
        return zone;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Player player = (Player) o;

        if (status != player.status) {
            return false;
        }
        if (zone == null ? player.zone != null : !zone.equals(player.zone)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = zone == null ? 0 : zone.hashCode();
        result = 31 * result + (status == null ? 0 : status.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "zone=" + zone +
                ", status=" + status +
                '}';
    }
}
