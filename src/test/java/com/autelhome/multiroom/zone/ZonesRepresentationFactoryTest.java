package com.autelhome.multiroom.zone;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Test;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Arrays;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZonesRepresentationFactoryTest {

    public static final String BASE_URI = "http://localhost:1234/";
    private final UriInfo uriInfo = getUriInfo();
    private final ZoneRepresentationFactory zoneRepresentationFactory = mock(ZoneRepresentationFactory.class);
    private final ZonesRepresentationFactory testSubject = new ZonesRepresentationFactory(uriInfo, zoneRepresentationFactory);

    private UriInfo getUriInfo() {
        final UriInfo result = mock(UriInfo.class);
        when(result.getBaseUri()).thenReturn(URI.create(BASE_URI));
        return result;
    }

    @Test
    public void newRepresentation() throws Exception {
        final StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();

        final Representation zone1Representation = representationFactory
                .withFlag(StandardRepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation(URI.create("/zones/zone1"))
                .withProperty("name", "zone1");

        final Representation zone2Representation = representationFactory
                .newRepresentation(URI.create("/zones/zone2"))
                .withProperty("name", "zone2");

        final String mrNamespace = "http://localhost:1234/multiroom-mpd/docs/#/relations/{rel}";

        final String expected = representationFactory
                .newRepresentation(URI.create(BASE_URI + "zones/"))
                .withNamespace("mr", mrNamespace)
                .withRepresentation("mr:zone", zone1Representation)
                .withRepresentation("mr:zone", zone2Representation)
                .toString(RepresentationFactory.HAL_JSON);

        final ZoneDto zone1 = new ZoneDto(null, "zone1", 7912, 1);
        final ZoneDto zone2 = new ZoneDto(null, "zone2", 7912, 1);

        when(zoneRepresentationFactory.newRepresentation(zone1)).thenReturn(zone1Representation);
        when(zoneRepresentationFactory.newRepresentation(zone2)).thenReturn(zone2Representation);

        final String actual = testSubject.newRepresentation(Arrays.asList(zone1, zone2)).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }
}