package com.autelhome.multiroom.util;

import com.autelhome.multiroom.zone.ZoneException;
import com.google.inject.Inject;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.UnsupportedEncodingException;

/**
 * Resolves the 'mr' namespace URL.
 *
 * @author xdeclercq
 */
public class MultiroomNamespaceResolver {

    public static final String DOCS_RELS_REL = "/docs/rels/{rel}";

    private final UriInfo uriInfo;
    private final URIDecoder uriDecoder;

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     * @param uriDecoder an {@link URIDecoder} instance
     */
    @Inject
    public MultiroomNamespaceResolver(final UriInfo uriInfo, final URIDecoder uriDecoder)
    {
        this.uriInfo = uriInfo;
        this.uriDecoder = uriDecoder;
    }

    /**
     * Resolves the 'mr' namespace URL.
     *
     * @return the 'mr' namespace URL.
     */
    public String resolve() {
        UriBuilder mrNamespaceUriBuilder = uriInfo.getBaseUriBuilder();

        try {
            return uriDecoder.decode(mrNamespaceUriBuilder.path(DOCS_RELS_REL).build());
        } catch (UnsupportedEncodingException e) {
            throw new ZoneException("Unable to decode URI", e);
        }

    }

}
