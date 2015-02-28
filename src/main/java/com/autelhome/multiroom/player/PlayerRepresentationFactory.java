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
     * @param zoneName the zone name
     * @return a new {@link Representation} of the player
     */
    public Representation newRepresentation(final String zoneName) {
        final String getPlayerResourceMethod = "getPlayerResource";
        final URI self = getBaseURIBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, getPlayerResourceMethod)
                .build(zoneName);

        final URI play = getBaseURIBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, getPlayerResourceMethod)
                .path(PlayerResource.class, "play")
                .build(zoneName);

        final URI pause = getBaseURIBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, getPlayerResourceMethod)
                .path(PlayerResource.class, "pause")
                .build(zoneName);

        final URI stop = getBaseURIBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, getPlayerResourceMethod)
                .path(PlayerResource.class, "stop")
                .build(zoneName);

        // TODO: embed status with link to socket resource


        return super.newRepresentation(self.toString())
                .withNamespace("mr", getMRNamespace())
                .withLink("mr:play", play)
                .withLink("mr:pause", pause)
                .withLink("mr:stop", stop);
    }

}
