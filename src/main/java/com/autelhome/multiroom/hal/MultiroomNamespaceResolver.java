package com.autelhome.multiroom.hal;

import com.google.inject.Inject;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Resolves the 'mr' namespace URL.
 *
 * @author xdeclercq
 */
public class MultiroomNamespaceResolver {

    private static final String DOCS_RELS_REL = "/docs/#/relations/{rel}";

    private final UriInfo uriInfo;

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     */
    @Inject
    public MultiroomNamespaceResolver(final UriInfo uriInfo)
    {
        this.uriInfo = uriInfo;
    }

    /**
     * Resolves the 'mr' namespace URL.
     *
     * @return the 'mr' namespace URL.
     */
    public String resolve() {

        final URI baseUri = uriInfo.getBaseUri();

        return String.format("http://%s:%d/multiroom-mpd/docs/#/relations/{rel}", baseUri.getHost(), baseUri.getPort());
    }

}
