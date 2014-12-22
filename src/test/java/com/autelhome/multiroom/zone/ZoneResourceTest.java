package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.hal.HalJsonMessageBodyWriter;
import com.autelhome.multiroom.hal.MultiroomNamespaceResolver;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class ZoneResourceTest {

    private ZoneService zoneService = mock(ZoneService.class);
    private final UriInfo uriInfo = mock(UriInfo.class);
    private ZoneRepresentationFactory zoneRepresentationFactory = new ZoneRepresentationFactory(uriInfo);
    private MultiroomNamespaceResolver multiroomNamespaceResolver = mock(MultiroomNamespaceResolver.class);
    private ZonesRepresentationFactory zonesRepresentationFactory = new ZonesRepresentationFactory(uriInfo, zoneRepresentationFactory, multiroomNamespaceResolver);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ZoneResource(zoneService, zonesRepresentationFactory))
            .addProvider(HalJsonMessageBodyWriter.class)
            .build();

    @Test
    public void get() throws Exception {
        String expected = Resources.toString(Resources.getResource(getClass(), "zones.json"), Charsets.UTF_8);

        SortedSet<Zone> zones = new TreeSet<>();
        zones.add(new Zone("Kitchen"));
        zones.add(new Zone("Bedroom"));

        when(zoneService.getAll()).thenReturn(zones);
        UriBuilder selfUriBuilder = UriBuilder.fromPath("/");
        UriBuilder mrNamespaceUriBuilder = UriBuilder.fromPath("/");
        UriBuilder zone1UriBuilder = UriBuilder.fromPath("/");
        UriBuilder zone2UriBuilder = UriBuilder.fromPath("/");
        when(uriInfo.getBaseUriBuilder()).thenReturn(selfUriBuilder, mrNamespaceUriBuilder, zone1UriBuilder, zone2UriBuilder);
        when(multiroomNamespaceResolver.resolve()).thenReturn("/docs/rels/{rel}");

        String actual = resources.client().resource("/zones").accept(RepresentationFactory.HAL_JSON).get(String.class);

        assertEquals(expected, actual, true);
    }
}