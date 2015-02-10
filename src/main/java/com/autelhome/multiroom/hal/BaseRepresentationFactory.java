package com.autelhome.multiroom.hal;

import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Base representation factory.
 *
 * @author xdeclercq
 */
public class BaseRepresentationFactory extends StandardRepresentationFactory {

	private final UriInfo uriInfo;


	/**
	 * Constructor.
	 *
	 * @param uriInfo the {@link UriInfo} related to the request
	 */
	protected  BaseRepresentationFactory(final UriInfo uriInfo) {
		withFlag(COALESCE_ARRAYS);
		this.uriInfo = uriInfo;
	}

	/**
	 * Returns the base {@link UriBuilder} based on the request.
	 *
	 * @return the base {@link UriBuilder} based on the request
	 */
	protected UriBuilder getBaseUriBuilder() {
		return uriInfo.getBaseUriBuilder();
	}

	/**
	 * Returns the 'mr' namespace URL.
	 *
	 * @return the 'mr' namespace URL
	 */
	protected String getMRNamespace() {
        final URI baseUri = uriInfo.getBaseUri();
        return String.format("http://%s:%d/multiroom-mpd/docs/#/relations/{rel}", baseUri.getHost(), baseUri.getPort());
	}
}
