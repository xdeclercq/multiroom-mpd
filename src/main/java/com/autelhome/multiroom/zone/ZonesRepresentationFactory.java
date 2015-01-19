package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.autelhome.multiroom.hal.MultiroomNamespaceResolver;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;

/**
 * {@link StandardRepresentationFactory} for {@link Zone}s.
 *
 * @author xdeclercq
 */
public class ZonesRepresentationFactory extends BaseRepresentationFactory
{
    private final ZoneRepresentationFactory zoneRepresentationFactory;

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     * @param zoneRepresentationFactory an {@link ZoneRepresentationFactory} instance
     * @param multiroomNamespaceResolver a {@link MultiroomNamespaceResolver} instance
     */
    @Inject
    public ZonesRepresentationFactory(final UriInfo uriInfo, final ZoneRepresentationFactory zoneRepresentationFactory, final MultiroomNamespaceResolver multiroomNamespaceResolver)
    {
        super(uriInfo, multiroomNamespaceResolver);
        this.zoneRepresentationFactory = zoneRepresentationFactory;
    }

    /**
     * Returns a new {@link Representation} of zones.
     *
     * @param zones the {@link Zone}s
     * @return a new {@link Representation} of the zones
     */
    public Representation newRepresentation(final Collection<Zone> zones)
    {
        final URI self = getBaseUriBuilder().path(ZoneResource.class).build();

        final Representation representation = newRepresentation(self);

        representation.withNamespace("mr", getMRNamespace());

        zones.forEach(
                (zone) -> representation.withRepresentation("mr:zone", zoneRepresentationFactory.newRepresentation(zone))
        );

        return representation;
    }

}
