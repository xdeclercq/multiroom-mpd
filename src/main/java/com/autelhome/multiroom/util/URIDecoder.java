package com.autelhome.multiroom.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

/**
 * Utility class to decode URI.
 *
 * @author xdeclercq
 */
public class URIDecoder {

    /**
     * Decodes an URI by using {@link URLDecoder} with UTF-8.
     *
     * @param uri an {@link URI}
     * @return the decoded {@link URI} as a {@link String}
     * @throws UnsupportedEncodingException if UTF-8 encoding is not supported
     */
    public String decode(final URI uri) throws UnsupportedEncodingException {
        return URLDecoder.decode(uri.toString(), "UTF-8");
    }
}
