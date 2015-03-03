package com.autelhome.multiroom.hal;

import com.google.common.base.Splitter;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Base representation factory.
 *
 * @author xdeclercq
 */
public class BaseRepresentationFactory extends StandardRepresentationFactory {

	private final URI baseURI;

	/**
	 * Constructor.
	 *
	 * @param uriInfo the {@link UriInfo} related to the request
	 */
	protected  BaseRepresentationFactory(final UriInfo uriInfo) {
		withFlag(COALESCE_ARRAYS);

		baseURI = uriInfo.getBaseUri();
	}

    /**
     * Constructor.
     *
     * @param uriRequest the request uri
     */
    protected BaseRepresentationFactory(final URI uriRequest) {
        final List<String> pathTokens = Splitter.on('/').splitToList(uriRequest.getPath());
        baseURI = URI.create(uriRequest.getScheme() + "://" + uriRequest.getAuthority() + "/" + pathTokens.get(1) + "/" + pathTokens.get(2));

        withFlag(COALESCE_ARRAYS);
    }

	/**
	 * Returns the base {@link UriBuilder} based on the request.
	 *
	 * @return the base {@link UriBuilder} based on the request
	 */
	protected UriBuilder getBaseURIBuilder() {
		return UriBuilder.fromUri(baseURI);
	}

	/**
	 * Returns the 'mr' namespace URL.
	 *
	 * @return the 'mr' namespace URL
	 */
	protected String getMRNamespace() {
        return getDocumentationBaseUri() + "relations/{rel}";
	}

    /**
     * Returns the documentation base URI.
     *
     * @return the documentation base URI
     */
    protected String getDocumentationBaseUri() {
        final URI baseUri = getBaseURIBuilder().build();
        return String.format("http://%s/multiroom-mpd/docs/#/", baseUri.getAuthority());
    }
}
