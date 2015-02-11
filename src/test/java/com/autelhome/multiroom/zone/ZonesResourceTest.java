package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.hal.HalJsonMessageBodyWriter;
import com.autelhome.multiroom.player.PlayerResourceFactory;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.ClientResponse;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class ZonesResourceTest {

    private final ZoneService zoneService = mock(ZoneService.class);
    private final UriInfo uriInfo = mock(UriInfo.class);
    private final ZoneRepresentationFactory zoneRepresentationFactory = new ZoneRepresentationFactory(uriInfo);
    private final ZonesRepresentationFactory zonesRepresentationFactory = new ZonesRepresentationFactory(uriInfo, zoneRepresentationFactory);
    private final PlayerResourceFactory playerResourceFactory = mock(PlayerResourceFactory.class);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ZonesResource(zoneService, zonesRepresentationFactory, zoneRepresentationFactory, playerResourceFactory))
            .addProvider(HalJsonMessageBodyWriter.class)
            .build();

    @Test
    public void get() throws Exception {
        final String expectedContent = Resources.toString(Resources.getResource(getClass(), "zones.json"), Charsets.UTF_8);

        final SortedSet<Zone> zones = new TreeSet<>();
        zones.add(new Zone("Kitchen"));
        zones.add(new Zone("Bedroom"));

        when(zoneService.getAll()).thenReturn(zones);

        final String baseURI = "http://server:6789/api";
        when(uriInfo.getBaseUriBuilder()).thenAnswer(i -> UriBuilder.fromPath(baseURI));
        when(uriInfo.getBaseUri()).thenReturn(URI.create(baseURI));

        final ClientResponse actualResponse = resources.client().resource("/zones").accept(RepresentationFactory.HAL_JSON).get(ClientResponse.class);

        final String actualContent = actualResponse.getEntity(String.class);

        assertEquals(expectedContent, actualContent, true);

        final int actualStatusCode = actualResponse.getStatus();

        assertThat(actualStatusCode).isEqualTo(200);
    }

    @Test
    public void getByName() throws Exception {

        final Zone bedroom = new Zone("Bedroom");

        when(zoneService.getByName("Bedroom")).thenReturn(bedroom);

        final String baseURI = "http://server:374/api";
        when(uriInfo.getBaseUriBuilder()).thenAnswer(i -> UriBuilder.fromPath(baseURI));
        when(uriInfo.getBaseUri()).thenReturn(URI.create(baseURI));

        final ClientResponse actualResponse = resources.client().resource("/zones/Bedroom").accept(RepresentationFactory.HAL_JSON).get(ClientResponse.class);

        final String actualContent = actualResponse.getEntity(String.class);

        final String expectedContent = Resources.toString(Resources.getResource(getClass(), "zone.json"), Charsets.UTF_8);

        assertEquals(expectedContent, actualContent, true);

        final int actualStatusCode = actualResponse.getStatus();

        assertThat(actualStatusCode).isEqualTo(200);
    }
}