package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.errors.ResourceNotFoundException;
import com.autelhome.multiroom.hal.HalJsonMessageBodyWriter;
import com.autelhome.multiroom.player.PlayerResourceFactory;
import com.autelhome.multiroom.util.ContextInjectableProvider;
import com.autelhome.multiroom.util.EventBus;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.ClientResponse;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class ZonesResourceTest {

    private final ZoneService zoneService = mock(ZoneService.class);
    private final UriInfo uriInfo = getUriInfo();

    private final ZoneRepresentationFactory zoneRepresentationFactory = new ZoneRepresentationFactory(uriInfo);

    private final ZonesRepresentationFactory zonesRepresentationFactory = new ZonesRepresentationFactory(uriInfo, zoneRepresentationFactory);
    private final PlayerResourceFactory playerResourceFactory = mock(PlayerResourceFactory.class);
    private final EventBus eventBus = mock(EventBus.class);
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ZonesResource(zoneService, zonesRepresentationFactory, zoneRepresentationFactory, playerResourceFactory, eventBus))
            .addProvider(new ContextInjectableProvider<>(HttpServletRequest.class, request))
            .addProvider(HalJsonMessageBodyWriter.class)
            .build();

    private UriInfo getUriInfo() {
        final UriInfo result = mock(UriInfo.class);
        when(result.getBaseUri()).thenReturn(URI.create("http://server:6789/api"));

        return result;
    }

    @Test
    public void getAll() throws Exception {
        final String expectedContent = Resources.toString(Resources.getResource(getClass(), "zones.json"), Charsets.UTF_8);

        final SortedSet<ZoneDto> zones = new TreeSet<>();
        zones.add(new ZoneDto(UUID.randomUUID(), "Kitchen", 7912, 1));
        zones.add(new ZoneDto(UUID.randomUUID(), "Bedroom", 234, 1));

        when(zoneService.getAll()).thenReturn(zones);



        final ClientResponse actualResponse = resources.client().resource("/zones").accept(RepresentationFactory.HAL_JSON).get(ClientResponse.class);

        final String actualContent = actualResponse.getEntity(String.class);

        assertEquals(expectedContent, actualContent, true);

        final int actualStatusCode = actualResponse.getStatus();

        assertThat(actualStatusCode).isEqualTo(200);
    }

    @Test
    public void getByName() throws Exception {

        final ZoneDto bedroom = new ZoneDto(UUID.randomUUID(), "Bedroom", 234, 1);

        when(zoneService.getByName("Bedroom")).thenReturn(Optional.of(bedroom));

        final ClientResponse actualResponse = resources.client().resource("/zones/Bedroom").accept(RepresentationFactory.HAL_JSON).get(ClientResponse.class);

        final String actualContent = actualResponse.getEntity(String.class);

        final String expectedContent = Resources.toString(Resources.getResource(getClass(), "zone.json"), Charsets.UTF_8);

        assertEquals(expectedContent, actualContent, true);

        final int actualStatusCode = actualResponse.getStatus();

        assertThat(actualStatusCode).isEqualTo(200);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getByNameUnknownZone() throws Exception {
        when(zoneService.getByName("UnknownZone")).thenReturn(Optional.empty());

        resources.client().resource("/zones/UnknownZone").accept(RepresentationFactory.HAL_JSON).get(ClientResponse.class);
    }

    @Test
    public void create() throws Exception {
        final ClientResponse actualResponse = resources.client().resource("/zones/").accept(RepresentationFactory.HAL_JSON).post(ClientResponse.class, "{\"name\":\"Bedroom\", \"mpdInstancePort\":\"234\"}");

        final String actualContent = actualResponse.getEntity(String.class);

        final String expectedContent = Resources.toString(Resources.getResource(getClass(), "zone.json"), Charsets.UTF_8);

        assertEquals(expectedContent, actualContent, true);

        final int actualStatusCode = actualResponse.getStatus();

        assertThat(actualStatusCode).isEqualTo(201);
    }
}