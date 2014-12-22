package com.autelhome.multiroom.hal;

import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

/**
 * Base representation factory.
 *
 * @author xdeclercq
 */
public class BaseRepresentationFactory extends StandardRepresentationFactory {

	/**
	 * Constructor.
	 */
	public BaseRepresentationFactory() {
		withFlag(COALESCE_ARRAYS);
	}
}
