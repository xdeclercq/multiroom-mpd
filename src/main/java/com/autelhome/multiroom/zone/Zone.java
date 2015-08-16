package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.errors.InvalidOperationException;
import com.autelhome.multiroom.player.*;
import com.autelhome.multiroom.playlist.ZonePlaylist;
import com.autelhome.multiroom.playlist.ZonePlaylistUpdated;
import com.autelhome.multiroom.util.AbstractAggregateRoot;
import com.autelhome.multiroom.util.Event;
import com.google.common.base.MoreObjects;

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
    private ZonePlaylist playlist;

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
     * @param playlist the initial playlist
     */
    public Zone(final UUID id, final String name, final int mpdInstancePortNumber, final PlayerStatus playerStatus, final ZonePlaylist playlist)
    {
        applyChange(new ZoneCreated(id, name, mpdInstancePortNumber, playerStatus, playlist));
    }

    /**
     * Changes the player current song.
     *
     * @param newCurrentSong the new current song
     */
    public void changeCurrentSong(final CurrentSong newCurrentSong) {
        applyChange(new CurrentSongUpdated(id, newCurrentSong));
    }


    /**
     * Changes the playlist.
     *
     * @param newPlaylist the new playlist
     */
    public void changePlaylist(final ZonePlaylist newPlaylist) {
        applyChange(new ZonePlaylistUpdated(id, newPlaylist));
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
     * Plays a song.
     *
     * @param position the position of the song to be played
     */
    public void playSongAtPosition(final int position) {
        final CurrentSong currentSong = new CurrentSong(playlist.getSongAtPosition(position).getSong(), position);
        applyChange(new SongAtPositionPlayed(id, currentSong));
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
        if (event instanceof Played) {
            applyPlayed();
        }
        if (event instanceof Paused) {
            applyPaused();
        }
        if (event instanceof Stopped) {
            applyStopped();
        }
        if (event instanceof ZonePlaylistUpdated) {
            applyZonePlaylistsUpdated((ZonePlaylistUpdated) event);
        }
        if (event instanceof SongAtPositionPlayed) {
            applySongPlayed();
        }
    }

    private void applyZoneCreated(final ZoneCreated zoneCreated) {
        id = zoneCreated.getId();
        name = zoneCreated.getName();
        mpdInstancePortNumber = zoneCreated.getMpdInstancePortNumber();
        playerStatus = zoneCreated.getPlayerStatus();
        playlist = zoneCreated.getPlaylist();
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

    private void applyZonePlaylistsUpdated(final ZonePlaylistUpdated zonePlaylistUpdated) {
        playlist = zonePlaylistUpdated.getNewPlaylist();
    }

    private void applySongPlayed() {
        playerStatus = PlayerStatus.PLAYING;
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
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("version", version)
                .add("mpdInstancePortNumber", mpdInstancePortNumber)
                .add("name", name)
                .add("playerStatus", playerStatus)
                .toString();
    }
}
