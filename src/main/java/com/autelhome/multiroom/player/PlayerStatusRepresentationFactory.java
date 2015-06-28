package com.autelhome.multiroom.player;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;

import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * {@link BaseRepresentationFactory} for a {@link PlayerStatus}.
 *
 * @author xdeclercq
 */
public class PlayerStatusRepresentationFactory extends BaseRepresentationFactory
{

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     */
    @Inject
    public PlayerStatusRepresentationFactory(final UriInfo uriInfo) {
        super(uriInfo);
    }

    /**
     * Constructor.
     *
     * @param requestURI a request URI
     */
    public PlayerStatusRepresentationFactory(final URI requestURI) {
        super(requestURI);
    }

    /**
     * Returns a new {@link Representation} of a player status.
     *
     * @param playerStatus a player status
     * @return a new {@link Representation} of the player status
     */
    public Representation newRepresentation(final PlayerStatus playerStatus, final String zoneName) {

        final URI self = getBaseURIBuilder()
                .path("..")
                .path(PlayerStatusEndpoint.class.getAnnotation(ServerEndpoint.class).value())
                .scheme("ws")
                .build(zoneName)
                .normalize();

        return newRepresentation(self)
                .withNamespace("mr", getMRNamespace())
                .withProperty("status", playerStatus.name());
    }

}
