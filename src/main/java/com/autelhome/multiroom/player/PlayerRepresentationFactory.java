package com.autelhome.multiroom.player;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.autelhome.multiroom.zone.ZoneResource;
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
        final URI self = getBaseUriBuilder()
                .path(ZoneResource.class)
                .path(ZoneResource.class, "getPlayerResource")
                .build(player.getZoneName());

        final URI play = getBaseUriBuilder()
                .path(ZoneResource.class)
                .path(ZoneResource.class, "getPlayerResource")
                .path(PlayerResource.class, "play")
                .build(player.getZoneName());

        final URI stop = getBaseUriBuilder()
                .path(ZoneResource.class)
                .path(ZoneResource.class, "getPlayerResource")
                .path(PlayerResource.class, "stop")
                .build(player.getZoneName());


        return newRepresentation(self)
                .withNamespace("mr", getMRNamespace())
                .withLink("mr:play", play)
                .withLink("mr:stop", stop);
    }

}
