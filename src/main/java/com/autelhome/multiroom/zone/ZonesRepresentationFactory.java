package com.autelhome.multiroom.zone;

import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;

/**
 * {@link StandardRepresentationFactory} for zones.
 *
 * @author xavier
 */
public class ZonesRepresentationFactory extends StandardRepresentationFactory
{

    private final UriInfo uriInfo;
    private final ZoneRepresentationFactory zoneRepresentationFactory;

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     * @param zoneRepresentationFactory an {@link ZoneRepresentationFactory} instance.
     */
    @Inject
    public ZonesRepresentationFactory(final UriInfo uriInfo, final ZoneRepresentationFactory zoneRepresentationFactory)
    {
        this.uriInfo = uriInfo;
        this.zoneRepresentationFactory = zoneRepresentationFactory;
    }

    /**
     * Returns a new {@link Representation} of zones.
     *
     * @param zones the {@link Zone}s
     * @return a new {@link Representation} of the zones
     */
    public Representation newRepresentation(Collection<Zone> zones)
    {
        URI self = uriInfo.getRequestUriBuilder().build();
        Representation representation = newRepresentation(self);

        zones.forEach(
                (zone) -> representation.withRepresentation("zone", zoneRepresentationFactory.newRepresentation(zone))
        );

        return representation;
    }
}
