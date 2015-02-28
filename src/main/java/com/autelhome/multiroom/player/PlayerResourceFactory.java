package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.ZoneDto;
import com.google.inject.Inject;

/**
 * Factory of a {@link PlayerResource}.
 *
 * @author xdeclercq
 */
public class PlayerResourceFactory
{

    private final PlayerRepresentationFactory playerRepresentationFactory;
    private final EventBus eventBus;

    /**
     * Constructor.
     *
     * @param playerRepresentationFactory a {@link PlayerRepresentationFactory} instance
     * @param eventBus the event bus
     */
    @Inject
    public PlayerResourceFactory(final PlayerRepresentationFactory playerRepresentationFactory, final EventBus eventBus)
    {
        this.playerRepresentationFactory = playerRepresentationFactory;
        this.eventBus = eventBus;
    }

    /**
     * Returns a new {@link PlayerResource} instance for a zone.
     *
     * @param zoneDto the zone
     * @return a new {@link PlayerResource} instance for the player related to the provided {@code zone}
     */
    public PlayerResource newInstance(final ZoneDto zoneDto)
    {
        return new PlayerResource(zoneDto, playerRepresentationFactory, eventBus);
    }
}
