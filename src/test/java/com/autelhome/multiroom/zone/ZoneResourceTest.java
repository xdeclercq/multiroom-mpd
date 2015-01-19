package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.hal.HalJsonMessageBodyWriter;
import com.autelhome.multiroom.hal.MultiroomNamespaceResolver;
import com.autelhome.multiroom.player.PlayerResourceFactory;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class ZoneResourceTest {

    private final ZoneService zoneService = mock(ZoneService.class);
    private final UriInfo uriInfo = mock(UriInfo.class);
    private final MultiroomNamespaceResolver multiroomNamespaceResolver = mock(MultiroomNamespaceResolver.class);
    private final ZoneRepresentationFactory zoneRepresentationFactory = new ZoneRepresentationFactory(uriInfo, multiroomNamespaceResolver);
    private final ZonesRepresentationFactory zonesRepresentationFactory = new ZonesRepresentationFactory(uriInfo, zoneRepresentationFactory, multiroomNamespaceResolver);
    private final PlayerResourceFactory playerResourceFactory = mock(PlayerResourceFactory.class);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ZoneResource(zoneService, zonesRepresentationFactory, zoneRepresentationFactory, playerResourceFactory))
            .addProvider(HalJsonMessageBodyWriter.class)
            .build();

    @Test
    @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
    public void get() throws Exception {
        final String expected = Resources.toString(Resources.getResource(getClass(), "zones.json"), Charsets.UTF_8);

        final SortedSet<Zone> zones = new TreeSet<>();
        zones.add(new Zone("Kitchen"));
        zones.add(new Zone("Bedroom"));

        when(zoneService.getAll()).thenReturn(zones);
        when(uriInfo.getBaseUriBuilder()).thenAnswer(new Answer<UriBuilder>() {
            @Override
            public UriBuilder answer(final InvocationOnMock invocation) throws Throwable {
                return UriBuilder.fromPath("/");
            }
        });
        when(multiroomNamespaceResolver.resolve()).thenReturn("/docs/rels/{rel}");

        final String actual = resources.client().resource("/zones").accept(RepresentationFactory.HAL_JSON).get(String.class);

        assertEquals(expected, actual, true);
    }

    @Test
    @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
    public void getByName() throws Exception {
        final String expected = Resources.toString(Resources.getResource(getClass(), "zone.json"), Charsets.UTF_8);

        final Zone bedroom = new Zone("Bedroom");

        when(zoneService.getByName("Bedroom")).thenReturn(bedroom);
        when(uriInfo.getBaseUriBuilder()).thenAnswer(new Answer<UriBuilder>() {
            @Override
            public UriBuilder answer(final InvocationOnMock invocation) throws Throwable {
                return UriBuilder.fromPath("/");
            }
        });
        when(multiroomNamespaceResolver.resolve()).thenReturn("/docs/rels/{rel}");

        final String actual = resources.client().resource("/zones/Bedroom").accept(RepresentationFactory.HAL_JSON).get(String.class);

        assertEquals(expected, actual, true);
    }
}