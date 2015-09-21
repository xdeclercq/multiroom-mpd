package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import java.net.URI;
import java.util.Collection;
import javax.ws.rs.core.UriInfo;

/**
 * {@link BaseRepresentationFactory} for {@link Zone}s.
 *
 * @author xdeclercq
 */
public class ZonesRepresentationFactory extends BaseRepresentationFactory {
    private final ZoneRepresentationFactory zoneRepresentationFactory;

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     * @param zoneRepresentationFactory an {@link ZoneRepresentationFactory} instance
     */
    @Inject
    public ZonesRepresentationFactory(final UriInfo uriInfo, final ZoneRepresentationFactory zoneRepresentationFactory) {
        super(uriInfo);
        this.zoneRepresentationFactory = zoneRepresentationFactory;
    }

    /**
     * Returns a new {@link Representation} of zones.
     *
     * @param zones the {@link Zone}s
     * @return a new {@link Representation} of the zones
     */
    public Representation newRepresentation(final Collection<ZoneDto> zones) {
        final URI self = getBaseURIBuilder().path(ZonesResource.class).build();

        final Representation representation = newRepresentation(self);

        representation.withNamespace("mr", getMRNamespace());

        zones.forEach(
                (zone) -> representation.withRepresentation("mr:zone", zoneRepresentationFactory.newRepresentation(zone))
        );

        return representation;
    }

}
