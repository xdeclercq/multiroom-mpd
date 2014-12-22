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

    private final UriInfo uriInfo;

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     */
    @Inject
    public ZoneRepresentationFactory(final UriInfo uriInfo)
    {
        super();
        this.uriInfo = uriInfo;
    }

    /**
     * Returns a new {@link Representation} of a zone.
     *
     * @param zone a zone
     * @return a new {@link Representation} of the zone
     */
    public Representation newRepresentation(final Zone zone)
    {
        URI self = uriInfo.getBaseUriBuilder().path(ZoneResource.class).build();
        Representation representation = newRepresentation(self)
                .withProperty("name", zone.getName());
        return representation;
    }

}
