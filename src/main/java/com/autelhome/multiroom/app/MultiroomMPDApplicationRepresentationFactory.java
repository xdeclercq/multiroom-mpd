package com.autelhome.multiroom.app;

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

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     */
    @Inject
    public MultiroomMPDApplicationRepresentationFactory(UriInfo uriInfo)
    {
        this.uriInfo = uriInfo;
    }

    @Override
    public Representation newRepresentation()
    {
        URI self = uriInfo.getRequestUriBuilder().path(MultiroomMPDApplicationResource.class).build();

        return newRepresentation(self);
    }
}
