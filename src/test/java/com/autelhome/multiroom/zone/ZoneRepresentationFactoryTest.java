package com.autelhome.multiroom.zone;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZoneRepresentationFactoryTest {

    private final UriInfo uriInfo = mock(UriInfo.class);

    @Test
    public void newRepresentation() throws Exception {
        StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();
        String expected = representationFactory
                .newRepresentation(URI.create("/zones"))
                .withProperty("name", "myZone")
                .toString(RepresentationFactory.HAL_JSON);

        ZoneRepresentationFactory zoneRepresentationFactory = new ZoneRepresentationFactory(uriInfo);

        final Zone zone = new Zone("myZone");

        when(uriInfo.getRequestUriBuilder()).thenReturn(UriBuilder.fromPath("/zones"));

        String actual = zoneRepresentationFactory.newRepresentation(zone).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);


    }
}