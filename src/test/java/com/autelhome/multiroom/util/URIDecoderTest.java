package com.autelhome.multiroom.util;

import org.junit.Test;

import javax.ws.rs.core.UriBuilder;

import static org.fest.assertions.api.Assertions.assertThat;

public class URIDecoderTest {

    private final URIDecoder testSubject = new URIDecoder();

    @Test
    public void decode() throws Exception {
        String expected = "/this/is/a/{template}/uri";
        String actual = testSubject.decode(UriBuilder.fromPath(expected).build());
        
        assertThat(actual).isEqualTo(expected);

    }
}