package com.autelhome.multiroom.app;

import com.autelhome.multiroom.hal.HalJsonMessageBodyWriter;
import com.autelhome.multiroom.util.ContextInjectableProvider;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class ApplicationResourceTest {

    private final UriInfo uriInfo = getUriInfo();
    private final ApplicationRepresentationFactory applicationRepresentationFactory = new ApplicationRepresentationFactory(uriInfo);

    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ApplicationResource(applicationRepresentationFactory))
            .addProvider(new ContextInjectableProvider<>(HttpServletRequest.class, request))
            .addProvider(HalJsonMessageBodyWriter.class)
            .build();


    private UriInfo getUriInfo() {
        final UriInfo result = mock(UriInfo.class);
        when(result.getBaseUri()).thenReturn(URI.create("http://localhost:1234"));
        return result;
    }

    @Test
    public void get() throws Exception {
        final String expected = Resources.toString(Resources.getResource(getClass(), "application.json"), Charsets.UTF_8);

        final String actual = resources.client().resource("/").accept(RepresentationFactory.HAL_JSON).get(String.class);

        assertEquals(expected, actual, true);
    }
}