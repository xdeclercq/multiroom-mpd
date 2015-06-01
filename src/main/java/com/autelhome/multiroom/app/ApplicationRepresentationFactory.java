package com.autelhome.multiroom.app;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.autelhome.multiroom.zone.ZonesResource;
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
public class ApplicationRepresentationFactory extends BaseRepresentationFactory {

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     */
    @Inject
    public ApplicationRepresentationFactory(final UriInfo uriInfo)
    {
        super(uriInfo);
    }

    @Override
    public Representation newRepresentation()
    {
        final String selfStr = getBaseURIBuilder().path(ApplicationResource.class).build().toString();

        final URI self = URI.create(selfStr.charAt(selfStr.length()-1) == '/' ? selfStr.substring(0, selfStr.length() - 1) : selfStr);

        final URI zonesURI = getBaseURIBuilder().path(ZonesResource.class).build();

        return newRepresentation(self)
                .withNamespace("mr", getMRNamespace())
                .withLink("mr:zones", zonesURI);
    }
}
