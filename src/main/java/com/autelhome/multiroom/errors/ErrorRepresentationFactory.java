package com.autelhome.multiroom.errors;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Representation factory for errors.
 *
 * @author xdeclercq
 */
public class ErrorRepresentationFactory extends BaseRepresentationFactory {

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     */
    @Inject
    public ErrorRepresentationFactory(final UriInfo uriInfo)
    {
        super(uriInfo);
    }


    /**
     * Constructor.
     *
     * @param requestURI the request URI
     */
    public ErrorRepresentationFactory(final URI requestURI) {
        super(requestURI);
    }

    /**
     * Returns a representation of the error.
     *
     * @param errorCode the error code
     * @param message the message
     * @return a representation of the error
     */
    public Representation newRepresentation(final ErrorCode errorCode, final String message) {

        final String errorInfo = getDocumentationBaseUri() + "errors/" + errorCode.getURLabel();

        return super.newRepresentation()
                .withProperty("errorCode", errorCode.getLabel())
                .withProperty("message", message)
                .withNamespace("mr", getMRNamespace())
                .withLink("mr:error-info", errorInfo);
    }
}
