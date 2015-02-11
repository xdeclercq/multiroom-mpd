package com.autelhome.multiroom.player;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.autelhome.multiroom.zone.ZonesResource;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * {@link BaseRepresentationFactory} for a {@link Player}.
 *
 * @author xdeclercq
 */
public class PlayerRepresentationFactory extends BaseRepresentationFactory
{

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     */
    @Inject
    public PlayerRepresentationFactory(final UriInfo uriInfo) {
        super(uriInfo);
    }

    /**
     * Returns a new {@link Representation} of a player.
     *
     * @param player a player
     * @return a new {@link Representation} of the player
     */
    public Representation newRepresentation(final Player player) {
        final String getPlayerResourceMethod = "getPlayerResource";
        final URI self = getBaseUriBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, getPlayerResourceMethod)
                .build(player.getZoneName());

        final URI play = getBaseUriBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, getPlayerResourceMethod)
                .path(PlayerResource.class, "play")
                .build(player.getZoneName());

        final URI pause = getBaseUriBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, getPlayerResourceMethod)
                .path(PlayerResource.class, "pause")
                .build(player.getZoneName());

        final URI stop = getBaseUriBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, getPlayerResourceMethod)
                .path(PlayerResource.class, "stop")
                .build(player.getZoneName());


        return newRepresentation(self)
                .withNamespace("mr", getMRNamespace())
                .withLink("mr:play", play)
                .withLink("mr:pause", pause)
                .withLink("mr:stop", stop);
    }

}
