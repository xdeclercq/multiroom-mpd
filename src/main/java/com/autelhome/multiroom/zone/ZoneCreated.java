package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.player.PlayerStatus;
import com.autelhome.multiroom.playlist.ZonePlaylist;
import com.autelhome.multiroom.util.AbstractEvent;
import java.util.Objects;
import java.util.UUID;

/**
 * Event triggered when a zone has been created.
 *
 * @author xdeclercq
 */
public class ZoneCreated extends AbstractEvent {

    private final String name;
    private final int mpdInstancePortNumber;
    private final PlayerStatus playerStatus;
    private final ZonePlaylist playlist;

    /**
     * Constructor.
     *
     * @param id the zone id
     * @param name the name of the zone
     * @param mpdInstancePortNumber the port number of the MPD instance related to the zone named {@code name}
     * @param playerStatus the initial player status
     * @param playlist the initial playlist
     */
    public ZoneCreated(final UUID id, final String name, final int mpdInstancePortNumber, final PlayerStatus playerStatus, final ZonePlaylist playlist) {
        super(id);
        this.name = name;
        this.mpdInstancePortNumber = mpdInstancePortNumber;
        this.playerStatus = playerStatus;
        this.playlist = playlist;
    }

    public String getName() {
        return name;
    }

    public int getMpdInstancePortNumber() {
        return mpdInstancePortNumber;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public ZonePlaylist getPlaylist() {
        return playlist;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ZoneCreated that = (ZoneCreated) o;

        if (id != that.id) {
            return false;
        }
        if (mpdInstancePortNumber != that.mpdInstancePortNumber) {
            return false;
        }
        if (!name.equals(that.name)) {
            return false;
        }
        if (playerStatus != that.playerStatus) {
            return false;
        }
        if (playlist != that.playlist) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mpdInstancePortNumber, playerStatus, playlist);
    }
}
