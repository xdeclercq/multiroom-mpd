package com.autelhome.multiroom.player;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.theoryinpractise.halbuilder.api.Representation;

import java.net.URI;

/**
 * {@link BaseRepresentationFactory} for a {@link PlayerStatus}.
 *
 * @author xdeclercq
 */
public class PlayerStatusRepresentationFactory extends BaseRepresentationFactory
{

    private final URI requestURI;

    /**
     * Constructor.
     *
     * @param requestURI a request URI
     */
    public PlayerStatusRepresentationFactory(final URI requestURI) {
        super(requestURI);
        this.requestURI = requestURI;
    }

    /**
     * Returns a new {@link Representation} of a player status.
     *
     * @param playerStatus a player status
     * @return a new {@link Representation} of the player status
     */
    public Representation newRepresentation(final PlayerStatus playerStatus) {

        return newRepresentation(requestURI)
                .withNamespace("mr", getMRNamespace())
                .withProperty("status", playerStatus.name());
    }

}
