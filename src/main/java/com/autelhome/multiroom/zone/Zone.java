package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.errors.InvalidOperationException;
import com.autelhome.multiroom.player.*;
import com.autelhome.multiroom.util.AbstractAggregateRoot;
import com.autelhome.multiroom.util.Event;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a zone.
 *
 * A zone is a location in your house which is associated to its own player and playlist.
 *
 * @author xdeclercq
 */
public class Zone extends AbstractAggregateRoot {

    private int mpdInstancePortNumber;
    private String name;
    private PlayerStatus playerStatus;

    /**
     * Constructor.
     */
    public Zone() {
        // do nothing
    }

    /**
     * Constructor.
     *
     * @param id the zone id
     * @param name the zone name
     * @param mpdInstancePortNumber the port number of the related mpd instance
     * @param playerStatus the initial player status
     */
    public Zone(final UUID id, final String name, final int mpdInstancePortNumber, final PlayerStatus playerStatus)
    {
        applyChange(new ZoneCreated(id, name, mpdInstancePortNumber, playerStatus));
    }

    /**
     * Changes the player status.
     *
     * @param newPlayerStatus the new player status
     * @throws InvalidOperationException if the new status and current status are the same
     */
    public void changePlayerStatus(final PlayerStatus newPlayerStatus) {
        if (playerStatus.equals(newPlayerStatus)) {
            throw new InvalidOperationException("Unable to change the player status to " + newPlayerStatus + " as it is already the current status");
        }
        applyChange(new PlayerStatusUpdated(id, newPlayerStatus));
    }

    /**
     * Plays.
     *
     * @throws InvalidOperationException if player is already playing
     */
    public void play() {
        if (playerStatus==PlayerStatus.PLAYING) {
            throw new InvalidOperationException("Unable to play as player is already playing");
        }
        applyChange(new Played(id));
    }

    /**
     * Pauses.
     *
     * @throws InvalidOperationException if player is not playing
     */
    public void pause() {
        if (playerStatus!=PlayerStatus.PLAYING) {
            throw new InvalidOperationException("Unable to pause as player is not playing");
        }
        applyChange(new Paused(id));
    }

    /**
     * Stops.
     *
     * @throws InvalidOperationException if player is already stopped
     */
    public void stop() {
        if (playerStatus==PlayerStatus.STOPPED) {
            throw new InvalidOperationException("Unable to stop as player is already stopped");
        }
        applyChange(new Stopped(id));
    }

    @Override
    protected void apply(final Event event) {
        if (event instanceof  ZoneCreated) {
            applyZoneCreated((ZoneCreated) event);
        }
        if (event instanceof  Played) {
            applyPlayed();
        }
        if (event instanceof  Paused) {
            applyPaused();
        }
        if (event instanceof  Stopped) {
            applyStopped();
        }
        if (event instanceof  PlayerStatusUpdated) {
            applyPlayerStatusUpdated((PlayerStatusUpdated) event);
        }
    }

    private void applyZoneCreated(final ZoneCreated zoneCreated) {
        id = zoneCreated.getId();
        name = zoneCreated.getName();
        mpdInstancePortNumber = zoneCreated.getMpdInstancePortNumber();
        playerStatus = zoneCreated.getPlayerStatus();
    }

    private void applyPlayed() {
        playerStatus = PlayerStatus.PLAYING;
    }

    private void applyPaused() {
        playerStatus = PlayerStatus.PAUSED;
    }

    private void applyStopped() {
        playerStatus = PlayerStatus.STOPPED;
    }

    private void applyPlayerStatusUpdated(final PlayerStatusUpdated playerStatusUpdated) {
        playerStatus = playerStatusUpdated.getNewPlayerStatus();
    }

    public int getMpdInstancePortNumber() {
        return mpdInstancePortNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Zone zone = (Zone) o;

        if (id == null ? zone.id != null : !id.equals(zone.id)) {
            return false;
        }
        if (mpdInstancePortNumber != zone.mpdInstancePortNumber) {
            return false;
        }
        if (name == null ? zone.name != null : !name.equals(zone.name)) {
            return false;
        }
        if (playerStatus != zone.playerStatus) {
            return false;
        }
        if (version != zone.version) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mpdInstancePortNumber, name, playerStatus, version);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .add("id", id)
                .add("version", version)
                .add("mpdInstancePortNumber", mpdInstancePortNumber)
                .add("name", name)
                .add("playerStatus", playerStatus)
                .toString();
    }
}
