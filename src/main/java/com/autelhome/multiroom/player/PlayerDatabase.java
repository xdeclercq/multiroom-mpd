package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.InstanceAlreadyPresentException;
import com.autelhome.multiroom.util.InstanceNotFoundException;
import com.google.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Database to store players.
 *
 * @author xdeclercq
 */
@Singleton
public class PlayerDatabase {

    private final Map<String, PlayerDto> playersByZoneName = new HashMap<>();
    private final Map<UUID, PlayerDto> playersByZoneId = new HashMap<>();

    /**
     * Adds a player to the database.
     *
     * @param playerDto a player
     */
    public void add(final PlayerDto playerDto) {
        if (playersByZoneId.containsKey(playerDto.getZoneId()) || playersByZoneName.containsKey(playerDto.getZoneName())) {
            throw new InstanceAlreadyPresentException("The player of zone '" + playerDto.getZoneId() + "' is already present in the database");
        }

        playersByZoneName.put(playerDto.getZoneName(), playerDto);
        playersByZoneId.put(playerDto.getZoneId(), playerDto);
    }

    /**
     * Updates a zone to the database.
     *
     * @param playerDto a player
     */
    public void update(final PlayerDto playerDto) {
        final UUID zoneId = playerDto.getZoneId();
        final PlayerDto currentPlayer = playersByZoneId.get(zoneId);
        if (currentPlayer == null) {
            throw new InstanceNotFoundException("The player of zone '" + zoneId + "' is not present in the database");
        }

        playersByZoneName.remove(currentPlayer.getZoneName());

        playersByZoneName.put(playerDto.getZoneName(), playerDto);
        playersByZoneId.put(zoneId, playerDto);
    }

    /**
     * Returns a player by its zone name.
     *
     * @param zoneName the name of a zone
     * @return a player by its zone name
     */
    public Optional<PlayerDto> getByZoneName(final String zoneName) {
        return Optional.ofNullable(playersByZoneName.get(zoneName));
    }

    /**
     * Returns a player by its zone id.
     *
     * @param zoneId a zone id
     * @return the player related to the zone of id {@code zoneId}
     */
    public Optional<PlayerDto> getByZoneId(final UUID zoneId) {
        return Optional.ofNullable(playersByZoneId.get(zoneId));
    }
}
