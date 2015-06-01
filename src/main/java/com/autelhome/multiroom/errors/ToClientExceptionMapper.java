package com.autelhome.multiroom.errors;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception mapper for the {@link ToClientException}.
 *
 * @author xdeclercq
 */
@Provider
public class ToClientExceptionMapper implements ExceptionMapper<ToClientException> {


    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(final ToClientException exception) {
        return Response.status(exception.getStatusCode()).entity(new ErrorRepresentationFactory(uriInfo).newRepresentation(exception.getErrorCode(), exception.getMessage())).build();
    }
}
