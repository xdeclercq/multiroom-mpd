package com.autelhome.multiroom.app;

import com.autelhome.multiroom.util.MultiroomNamespaceResolver;
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
public class MultiroomMPDApplicationRepresentationFactory extends StandardRepresentationFactory
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
        this.uriInfo = uriInfo;
        this.multiroomNamespaceResolver = multiroomNamespaceResolver;
        withFlag(COALESCE_ARRAYS);
    }

    @Override
    public Representation newRepresentation()
    {
        URI self = uriInfo.getBaseUriBuilder().path(MultiroomMPDApplicationResource.class).build();

        String mrNamespace = multiroomNamespaceResolver.resolve();

        URI zonesURI = uriInfo.getBaseUriBuilder().path(ZoneResource.class).build();

        return newRepresentation(self)
                .withNamespace("mr", mrNamespace)
                .withLink("mr:zones", zonesURI);
    }
}
