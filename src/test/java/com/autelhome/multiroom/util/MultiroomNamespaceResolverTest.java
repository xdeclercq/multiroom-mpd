package com.autelhome.multiroom.util;

import com.autelhome.multiroom.hal.MultiroomNamespaceResolver;
import com.autelhome.multiroom.zone.ZoneException;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import java.io.UnsupportedEncodingException;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MultiroomNamespaceResolverTest {

    private UriInfo uriInfo = mock(UriInfo.class);
    private URIDecoder uriDecoder = mock(URIDecoder.class);
    private final MultiroomNamespaceResolver testSubject = new MultiroomNamespaceResolver(uriInfo, uriDecoder);

    @Test
    public void resolve() throws Exception {
        when(uriInfo.getBaseUriBuilder()).thenReturn(UriBuilder.fromPath("/base/uri/"));
        String expected = "/base/uri/docs/rels/{rel}";
        when(uriDecoder.decode(UriBuilder.fromPath(expected).build())).thenReturn(expected);

        String actual = testSubject.resolve();

        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = ZoneException.class)
    public void resolveShouldThrowZoneExceptionWhenURIDecoderThrowsException() throws Exception {
        when(uriInfo.getBaseUriBuilder()).thenReturn(UriBuilder.fromPath("/base/uri/"));
        String expected = "/base/uri/docs/rels/{rel}";
        when(uriDecoder.decode(UriBuilder.fromPath("/base/uri/docs/rels/{rel}").build())).thenThrow(new UnsupportedEncodingException("unsupported"));

        testSubject.resolve();
    }
}