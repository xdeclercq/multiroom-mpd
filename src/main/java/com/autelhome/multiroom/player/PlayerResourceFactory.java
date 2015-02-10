package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.Zone;
import com.google.inject.Inject;

/**
 * Factory of a {@link PlayerResource}.
 *
 * @author xdeclercq
 */
public class PlayerResourceFactory
{

    private final PlayerProvider playerProvider;
    private final PlayerRepresentationFactory playerRepresentationFactory;
    private final EventBus eventBus;

    /**
     * Constructor.
     *
     * @param playerProvider a {@link PlayerProvider} instance
     * @param playerRepresentationFactory a {@link PlayerRepresentationFactory} instance
     * @param eventBus the eventBus.
     */
    @Inject
    public PlayerResourceFactory(final PlayerProvider playerProvider, final PlayerRepresentationFactory playerRepresentationFactory, final EventBus eventBus)
    {
        this.playerProvider = playerProvider;
        this.playerRepresentationFactory = playerRepresentationFactory;
        this.eventBus = eventBus;
    }

    /**
     * Returns a new {@link PlayerResource} instance for a zone.
     *
     * @param zone a zone
     * @return a new {@link PlayerResource} instance for the player related to the provided {@code zone}
     */
    public PlayerResource newInstance(final Zone zone)
    {
        return new PlayerResource(playerProvider.getPlayer(zone), playerRepresentationFactory, eventBus);
    }
}
