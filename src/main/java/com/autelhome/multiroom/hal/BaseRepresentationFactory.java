package com.autelhome.multiroom.hal;

import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Base representation factory.
 *
 * @author xdeclercq
 */
public class BaseRepresentationFactory extends StandardRepresentationFactory {

	private final UriInfo uriInfo;
	private final MultiroomNamespaceResolver multiroomNamespaceResolver;

	/**
	 * Constructor.
	 *
	 * @param uriInfo the {@link UriInfo} related to the request
	 */
	protected BaseRepresentationFactory(final UriInfo uriInfo) {
		this(uriInfo, null);
	}

	/**
	 * Constructor.
	 *
	 * @param uriInfo the {@link UriInfo} related to the request
	 * @param multiroomNamespaceResolver a {@link MultiroomNamespaceResolver} instance
	 */
	protected  BaseRepresentationFactory(final UriInfo uriInfo, final MultiroomNamespaceResolver multiroomNamespaceResolver) {
		withFlag(COALESCE_ARRAYS);
		this.uriInfo = uriInfo;
		this.multiroomNamespaceResolver = multiroomNamespaceResolver;
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
		return multiroomNamespaceResolver.resolve();
	}
}
