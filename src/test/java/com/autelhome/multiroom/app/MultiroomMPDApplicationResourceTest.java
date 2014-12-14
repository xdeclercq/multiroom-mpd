package com.autelhome.multiroom.app;

import com.autelhome.multiroom.util.HalJsonMessageBodyWriter;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class MultiroomMPDApplicationResourceTest {

    private final UriInfo uriInfo = mock(UriInfo.class);
    private final MultiroomMPDApplicationRepresentationFactory multiroomMPDApplicationRepresentationFactory = new MultiroomMPDApplicationRepresentationFactory(uriInfo);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new MultiroomMPDApplicationResource(multiroomMPDApplicationRepresentationFactory))
            .addProvider(HalJsonMessageBodyWriter.class)
            .build();

    @Test
    public void get() throws Exception {
        String expected = Resources.toString(Resources.getResource(getClass(), "application.json"), Charsets.UTF_8);

        final UriBuilder uriBuilder = UriBuilder.fromUri("/");
        when(uriInfo.getRequestUriBuilder()).thenReturn(uriBuilder);

        String actual = resources.client().resource("/").accept(RepresentationFactory.HAL_JSON).get(String.class);

        assertEquals(expected, actual, true);
    }
}