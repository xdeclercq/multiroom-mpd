package com.autelhome.multiroom.player;

import com.autelhome.multiroom.socket.SocketBroadcaster;
import com.autelhome.multiroom.util.InstanceNotFoundException;
import com.autelhome.multiroom.zone.ZoneCreated;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Optional;
import java.util.UUID;

/**
 * View of players.
 *
 * @author xdeclercq
 */
@Singleton
public class PlayersView {

    private final PlayerDatabase playerDatabase;
    private final SocketBroadcaster socketBroadcaster;

    /**
     * Constructor.
     *
     * @param playerDatabase the player database
     * @param socketBroadcaster the socket broadcaster
     */
    @Inject
    public PlayersView(final PlayerDatabase playerDatabase, final SocketBroadcaster socketBroadcaster) {
        this.playerDatabase = playerDatabase;
        this.socketBroadcaster = socketBroadcaster;
    }

    /**
     * Updates database with the created player.
     *
     * @param zoneCreated a zone created event
     */
    public void handleCreated(final ZoneCreated zoneCreated) {
        playerDatabase.add(new PlayerDto(zoneCreated.getId(), zoneCreated.getName(), zoneCreated.getPlayerStatus()));
    }

    /**
     * Updates database with a played event.
     *
     * @param played a played event
     */
    public void handlePlayed(final Played played) {
        handleNewPlayerStatus(played.getId(), PlayerStatus.PLAYING);
    }

    /**
     * Updates database with a song played event.
     *
     * @param songAtPositionPlayed a song played event
     */
    public void handleSongPlayed(final SongAtPositionPlayed songAtPositionPlayed) {
        final UUID zoneId = songAtPositionPlayed.getId();
        final Optional<PlayerDto> playerOptional = playerDatabase.getByZoneId(zoneId);
        if (!playerOptional.isPresent()) {
            throw new InstanceNotFoundException("The zone '" + zoneId + "' is not present in the database");
        }
        final PlayerDto player = playerOptional.get();
        final String zoneName = player.getZoneName();
        final PlayerDto newPlayer = new PlayerDto(zoneId, zoneName, PlayerStatus.PLAYING, songAtPositionPlayed.getCurrentSong());
        playerDatabase.update(newPlayer);
        broadcast(zoneName, PlayerStatus.PLAYING);
    }

    /**
     * Updates database with a paused event.
     *
     * @param paused a paused event
     */
    public void handlePaused(final Paused paused) {
        handleNewPlayerStatus(paused.getId(), PlayerStatus.PAUSED);
    }

    /**
     * Updates database with a stopped event.
     *
     * @param stopped a stopped event
     */
    public void handleStopped(final Stopped stopped) {
        handleNewPlayerStatus(stopped.getId(), PlayerStatus.STOPPED);
    }

    /**
     * Updates database with a player status updated event.
     *
     * @param playerStatusUpdated a player status updated event
     */
    public void handlePlayerStatusUpdated(final PlayerStatusUpdated playerStatusUpdated) {
        handleNewPlayerStatus(playerStatusUpdated.getId(), playerStatusUpdated.getNewPlayerStatus());
    }

    /**
     * Updates database with a current song updated event.
     *
     * @param currentSongUpdated a current song updated event
     */
    public void handleCurrentSongUpdated(final CurrentSongUpdated currentSongUpdated) {
        final UUID zoneId = currentSongUpdated.getId();
        final Optional<PlayerDto> playerOptional = playerDatabase.getByZoneId(zoneId);
        if (!playerOptional.isPresent()) {
            throw new InstanceNotFoundException("The zone '" + zoneId + "' is not present in the database");
        }
        final PlayerDto player = playerOptional.get();
        final String zoneName = player.getZoneName();
        final PlayerDto newPlayer = new PlayerDto(zoneId, zoneName, player.getStatus(), currentSongUpdated.getNewCurrentSong());
        playerDatabase.update(newPlayer);
    }

    private void handleNewPlayerStatus(final UUID zoneId, final PlayerStatus newPlayerStatus) {
        final Optional<PlayerDto> playerOptional = playerDatabase.getByZoneId(zoneId);
        if (!playerOptional.isPresent()) {
            throw new InstanceNotFoundException("The zone '" + zoneId + "' is not present in the database");
        }
        final PlayerDto player = playerOptional.get();
        final String zoneName = player.getZoneName();
        final PlayerDto newPlayer = new PlayerDto(zoneId, zoneName, newPlayerStatus);
        playerDatabase.update(newPlayer);
        broadcast(zoneName, newPlayerStatus);
    }

    private void broadcast(final String zoneName, final PlayerStatus playerStatus) {
        final String key = String.format(PlayerStatusEndpoint.WS_PLAYER_STATUS_RESOURCE_ID_FORMAT, zoneName);
        socketBroadcaster.broadcast(key, playerStatus);
    }
}
