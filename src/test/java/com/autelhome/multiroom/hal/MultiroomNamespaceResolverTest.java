package com.autelhome.multiroom.hal;

import org.junit.Test;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MultiroomNamespaceResolverTest {

    private final UriInfo uriInfo = mock(UriInfo.class);
    private final MultiroomNamespaceResolver testSubject = new MultiroomNamespaceResolver(uriInfo);

    @Test
    public void resolve() throws Exception {
        when(uriInfo.getBaseUri()).thenReturn(URI.create("http://myserver:1234/api/"));
        final String expected = "http://myserver:1234/multiroom-mpd/docs/#/relations/{rel}";

        final String actual = testSubject.resolve();

        assertThat(actual).isEqualTo(expected);
    }
}