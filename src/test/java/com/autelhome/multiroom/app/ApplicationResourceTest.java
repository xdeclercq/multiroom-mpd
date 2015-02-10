package com.autelhome.multiroom.app;

import com.autelhome.multiroom.hal.HalJsonMessageBodyWriter;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import java.net.URI;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class ApplicationResourceTest {

    private final UriInfo uriInfo = mock(UriInfo.class);
    private final ApplicationRepresentationFactory applicationRepresentationFactory = new ApplicationRepresentationFactory(uriInfo);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ApplicationResource(applicationRepresentationFactory))
            .addProvider(HalJsonMessageBodyWriter.class)
            .build();

    @Test
    public void get() throws Exception {
        final String expected = Resources.toString(Resources.getResource(getClass(), "application.json"), Charsets.UTF_8);

        final String baseURI = "http://localhost:1234";
        final UriBuilder uriBuilder = UriBuilder.fromUri(baseURI);
        when(uriInfo.getBaseUriBuilder()).thenReturn(uriBuilder);
        when(uriInfo.getBaseUri()).thenReturn(URI.create(baseURI));

        final String actual = resources.client().resource("/").accept(RepresentationFactory.HAL_JSON).get(String.class);

        assertEquals(expected, actual, true);
    }
}