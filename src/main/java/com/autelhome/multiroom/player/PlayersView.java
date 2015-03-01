package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.InstanceNotFoundException;
import com.autelhome.multiroom.util.SocketBroadcaster;
import com.autelhome.multiroom.zone.ZoneCreated;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.atmosphere.cpr.BroadcasterFactory;

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
    private final BroadcasterFactory broadcasterFactory;

    /**
     * Constructor.
     *
     * @param playerDatabase the player database
     * @param broadcasterFactory the broadcaster factory
     */
    @Inject
    public PlayersView(final PlayerDatabase playerDatabase, final BroadcasterFactory broadcasterFactory) {
        this.playerDatabase = playerDatabase;
        this.broadcasterFactory = broadcasterFactory;
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
        final SocketBroadcaster broadcaster = broadcasterFactory.lookup(SocketBroadcaster.class, String.format(PlayerStatusSocketResource.WS_PLAYER_STATUS_RESOURCE_ID_FORMAT, zoneName));
        if( broadcaster != null ) {

            broadcaster.broadcastEntity(playerStatus);
        }
    }
}