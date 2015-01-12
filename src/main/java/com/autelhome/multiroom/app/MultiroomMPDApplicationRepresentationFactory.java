package com.autelhome.multiroom.app;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.autelhome.multiroom.hal.MultiroomNamespaceResolver;
import com.autelhome.multiroom.zone.ZoneResource;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * {@link StandardRepresentationFactory} of the application.
 *
 * @author xdeclercq
 */
public class MultiroomMPDApplicationRepresentationFactory extends BaseRepresentationFactory
{

    private final UriInfo uriInfo;
    private final MultiroomNamespaceResolver multiroomNamespaceResolver;

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     * @param multiroomNamespaceResolver a {@link MultiroomNamespaceResolver} instance
     */
    @Inject
    public MultiroomMPDApplicationRepresentationFactory(final UriInfo uriInfo, final MultiroomNamespaceResolver multiroomNamespaceResolver)
    {
        super();
        this.uriInfo = uriInfo;
        this.multiroomNamespaceResolver = multiroomNamespaceResolver;
    }

    @Override
    public Representation newRepresentation()
    {
        final URI self = uriInfo.getBaseUriBuilder().path(MultiroomMPDApplicationResource.class).build();

        final String mrNamespace = multiroomNamespaceResolver.resolve();

        final URI zonesURI = uriInfo.getBaseUriBuilder().path(ZoneResource.class).build();

        return newRepresentation(self)
                .withNamespace("mr", mrNamespace)
                .withLink("mr:zones", zonesURI);
    }
}
