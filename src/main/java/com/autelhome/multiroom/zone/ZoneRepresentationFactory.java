package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * {@link StandardRepresentationFactory} for a {@link Zone}.
 *
 * @author xavier
 */
public class ZoneRepresentationFactory extends BaseRepresentationFactory
{

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     */
    @Inject
    public ZoneRepresentationFactory(final UriInfo uriInfo) {
        super(uriInfo);
    }

    /**
     * Returns a new {@link Representation} of a zone.
     *
     * @param zone a zone
     * @return a new {@link Representation} of the zone
     */
    public Representation newRepresentation(final Zone zone) {
        final URI self = getBaseUriBuilder()
                .path(ZoneResource.class)
                .path(ZoneResource.class, "getByName")
                .build(zone.getName());

        final URI player = getBaseUriBuilder()
                .path(ZoneResource.class)
                .path(ZoneResource.class, "getPlayerResource")
                .build(zone.getName());

        return newRepresentation(self)
                .withNamespace("mr", getMRNamespace())
                .withProperty("name", zone.getName())
                .withLink("mr:player", player);
    }

}
