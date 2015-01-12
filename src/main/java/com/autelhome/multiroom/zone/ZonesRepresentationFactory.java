package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.autelhome.multiroom.hal.MultiroomNamespaceResolver;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import javax.ws.rs.core.UriBuilder;
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
    private final UriInfo uriInfo;
    private final ZoneRepresentationFactory zoneRepresentationFactory;
    private final MultiroomNamespaceResolver multiroomNamespaceResolver;

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
        super();
        this.uriInfo = uriInfo;
        this.zoneRepresentationFactory = zoneRepresentationFactory;
        this.multiroomNamespaceResolver = multiroomNamespaceResolver;
    }

    /**
     * Returns a new {@link Representation} of zones.
     *
     * @param zones the {@link Zone}s
     * @return a new {@link Representation} of the zones
     */
    public Representation newRepresentation(final Collection<Zone> zones)
    {
        final UriBuilder selfUriBuilder = uriInfo.getBaseUriBuilder();
        final URI self = selfUriBuilder.path(ZoneResource.class).build();

        final Representation representation = newRepresentation(self);

        final String mrNamespace = multiroomNamespaceResolver.resolve();
        representation.withNamespace("mr", mrNamespace);

        zones.forEach(
                (zone) -> representation.withRepresentation("mr:zone", zoneRepresentationFactory.newRepresentation(zone))
        );

        return representation;
    }

}
