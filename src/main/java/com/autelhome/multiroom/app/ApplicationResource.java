package com.autelhome.multiroom.app;

import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * REST resource for the application providing links to the main entry points.
 *
 * @author xdeclercq
 */
@Path("")
@Produces({RepresentationFactory.HAL_JSON})
public class ApplicationResource
{

    private final ApplicationRepresentationFactory applicationRepresentationFactory;

    /**
     * Constructor.
     *
     * @param applicationRepresentationFactory a {@link ApplicationRepresentationFactory} isntance
     */
    @Inject
    public ApplicationResource(final ApplicationRepresentationFactory applicationRepresentationFactory) {
        this.applicationRepresentationFactory = applicationRepresentationFactory;
    }

    /**
     * Returns a representation of the application with the links to the main entry points.
     *
     * @return a representation of the application with the links to the following entry points:
     * <ul>
     *     <li>zones</li>
     * </ul>
     */
    @GET
    public Response get() {
        final Representation representation = applicationRepresentationFactory
                .newRepresentation();
        return Response.ok(representation).build();
    }
}
