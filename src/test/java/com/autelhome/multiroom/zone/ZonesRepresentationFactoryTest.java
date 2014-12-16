package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.util.URIDecoder;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Arrays;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZonesRepresentationFactoryTest {

    private final UriInfo uriInfo = mock(UriInfo.class);
    private final ZoneRepresentationFactory zoneRepresentationFactory = mock(ZoneRepresentationFactory.class);
    private final URIDecoder uriDecoder = mock(URIDecoder.class);
    private final ZonesRepresentationFactory testSubject = new ZonesRepresentationFactory(uriInfo, zoneRepresentationFactory, uriDecoder);

    @Test
    public void newRepresentation() throws Exception {
        final StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();

        final Representation zone1Representation = representationFactory
                .newRepresentation(URI.create("/zones/zone1"))
                .withProperty("name", "zone1");

        final Representation zone2Representation = representationFactory
                .newRepresentation(URI.create("/zones/zone2"))
                .withProperty("name", "zone2");

        String mrNamespace = "/docs/rels/{rel}";

        final String expected = representationFactory
                .newRepresentation(URI.create("/zones"))
                .withNamespace("mr", mrNamespace)
                .withRepresentation("mr:zone", zone1Representation)
                .withRepresentation("mr:zone", zone2Representation)
                .toString(RepresentationFactory.HAL_JSON);

        Zone zone1 = new Zone("zone1");
        Zone zone2 = new Zone("zone2");

        when(uriInfo.getBaseUriBuilder()).thenReturn(UriBuilder.fromPath("/"), UriBuilder.fromPath("/"));
        when(zoneRepresentationFactory.newRepresentation(zone1)).thenReturn(zone1Representation);
        when(zoneRepresentationFactory.newRepresentation(zone2)).thenReturn(zone2Representation);
        when(uriDecoder.decode(UriBuilder.fromPath(mrNamespace).build())).thenReturn(mrNamespace);

        String actual = testSubject.newRepresentation(Arrays.asList(zone1, zone2)).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = ZoneException.class)
    public void newRepresentationShouldThrowZoneException() throws Exception {
        final StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();


        String mrNamespace = "/docs/rels/{rel}";

        Zone zone1 = new Zone("zone1");
        Zone zone2 = new Zone("zone2");

        when(uriInfo.getBaseUriBuilder()).thenReturn(UriBuilder.fromPath("/"), UriBuilder.fromPath("/"));
        when(uriDecoder.decode(UriBuilder.fromPath(mrNamespace).build())).thenThrow(new UnsupportedEncodingException("unsupported"));

        testSubject.newRepresentation(Arrays.asList(zone1, zone2)).toString(RepresentationFactory.HAL_JSON);
    }
}