package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.util.URIDecoder;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Collection;

/**
 * {@link StandardRepresentationFactory} for {@link Zone}s.
 *
 * @author xdeclercq
 */
public class ZonesRepresentationFactory extends StandardRepresentationFactory
{

    public static final String DOCS_RELS_REL = "/docs/rels/{rel}";

    private final UriInfo uriInfo;
    private final ZoneRepresentationFactory zoneRepresentationFactory;
    private final URIDecoder uriDecoder;

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     * @param zoneRepresentationFactory an {@link ZoneRepresentationFactory} instance
     * @param uriDecoder a {@link URIDecoder} instance
     */
    @Inject
    public ZonesRepresentationFactory(final UriInfo uriInfo, final ZoneRepresentationFactory zoneRepresentationFactory, final URIDecoder uriDecoder)
    {
        this.uriInfo = uriInfo;
        this.zoneRepresentationFactory = zoneRepresentationFactory;
        this.uriDecoder = uriDecoder;
    }

    /**
     * Returns a new {@link Representation} of zones.
     *
     * @param zones the {@link Zone}s
     * @return a new {@link Representation} of the zones
     */
    public Representation newRepresentation(final Collection<Zone> zones)
    {
        UriBuilder selfUriBuilder = uriInfo.getBaseUriBuilder();
        URI self = selfUriBuilder.path(ZoneResource.class).build();

        UriBuilder mrNamespaceUriBuilder = uriInfo.getBaseUriBuilder();

        final Representation representation = newRepresentation(self);

        try {
            final String mrNamespace  = uriDecoder.decode(mrNamespaceUriBuilder.path(DOCS_RELS_REL).build());
            representation.withNamespace("mr", mrNamespace);
        } catch (UnsupportedEncodingException e) {
            throw new ZoneException("Unable to decode URI", e);
        }


        zones.forEach(
                (zone) -> representation.withRepresentation("mr:zone", zoneRepresentationFactory.newRepresentation(zone))
        );

        return representation;
    }

}
