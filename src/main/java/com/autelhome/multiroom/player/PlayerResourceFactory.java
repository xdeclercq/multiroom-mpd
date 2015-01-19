package com.autelhome.multiroom.player;

import com.autelhome.mpd.song.SongRepresentationFactory;
import com.google.inject.Inject;

/**
 * Factory of a {@link PlayerResource}.
 *
 * @author xavier
 */
public class PlayerResourceFactory
{

    private final PlayerService playerService;
    private final PlayerRepresentationFactory playerRepresentationFactory;
    private SongRepresentationFactory songRepresentationFactory;

    /**
     * Constructor.
     *
     * @param playerService a {@link PlayerService} instance
     * @param playerRepresentationFactory a {@link PlayerRepresentationFactory} instance
     */
    @Inject
    public PlayerResourceFactory(final PlayerService playerService, final PlayerRepresentationFactory playerRepresentationFactory)
    {
        this.playerService = playerService;
        this.playerRepresentationFactory = playerRepresentationFactory;
    }

    /**
     * Returns a new {@link PlayerResource} instance for a zone.
     *
     * @param zoneName the name of a zone
     * @return a new {@link PlayerResource} instance for the zone named {@code zoneName}
     */
    public PlayerResource newInstance(final String zoneName)
    {
        return new PlayerResource(zoneName, playerService, playerRepresentationFactory);
    }
}
