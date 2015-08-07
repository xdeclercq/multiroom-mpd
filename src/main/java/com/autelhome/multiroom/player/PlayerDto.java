package com.autelhome.multiroom.player;

import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a player data transfer object.
 *
 * A player servers to play music in a zone.
 *
 * @author xdeclercq
 */
public class PlayerDto
{

    private final UUID zoneId;
    private final String zoneName;
    private final PlayerStatus status;
    private final CurrentSong currentSong;

    /**
     * Constructor.
     *
     * @param zoneId the id of the zone
     * @param zoneName the name of the zone
     * @param status the player status
     */
    public PlayerDto(final UUID zoneId, final String zoneName, final PlayerStatus status)
    {
        this(zoneId, zoneName, status, null);
    }

    /**
     * Constructor.
     *
     * @param zoneId the id of the zone
     * @param zoneName the name of the zone
     * @param status the player status
     * @param currentSong the current song
     */
    public PlayerDto(final UUID zoneId, final String zoneName, final PlayerStatus status, final CurrentSong currentSong)
    {
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.status = status;
        this.currentSong = currentSong;
    }

    public String getZoneName()
    {
        return zoneName;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public UUID getZoneId() {
        return zoneId;
    }

    public CurrentSong getCurrentSong() {
        return currentSong;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(zoneId, zoneName, status, currentSong);
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final PlayerDto playerDto = (PlayerDto) o;

        if (zoneId != playerDto.zoneId) {
            return false;
        }

        if (status != playerDto.status) {
            return false;
        }
        if (!Objects.equals(zoneName, playerDto.zoneName)) {
            return false;
        }
        return Objects.equals(currentSong, playerDto.currentSong);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("zoneId", zoneId)
                .add("zoneName", zoneName)
                .add("status", status)
                .add("currentSong", currentSong)
                .toString();
    }
}
