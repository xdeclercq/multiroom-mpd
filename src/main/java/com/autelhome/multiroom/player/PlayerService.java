package com.autelhome.multiroom.player;

import com.google.inject.Inject;
import java.util.Optional;

/**
 * Service to fetch players.
 *
 * @author xdeclercq
 */
public class PlayerService {

    private final PlayerDatabase playerDatabase;

    /**
     * Constructor.
     *
     * @param playerDatabase the player database
     */
    @Inject
    public PlayerService(final PlayerDatabase playerDatabase) {
        this.playerDatabase = playerDatabase;
    }

    /**
     * Returns a player by its zone name.
     *
     * @param zoneName the name of a zone
     * @return a player by its zone name
     */
    public Optional<PlayerDto> getPlayerByZoneName(final String zoneName) {
        return playerDatabase.getByZoneName(zoneName);
    }
}
