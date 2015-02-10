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
    private final ZoneRepresentationFactory testSubject = new ZoneRepresentationFactory(uriInfo);

    @Test
    public void newRepresentation() throws Exception {
        final StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();


        final String baseURI = "http://myserver:1234/api";
        final String selfLink = baseURI + "/zones/myZone";
        final String playerLink = selfLink + "/player";

        when(uriInfo.getBaseUriBuilder()).thenAnswer(i -> UriBuilder.fromUri(baseURI));
        when(uriInfo.getBaseUri()).thenReturn(URI.create(baseURI));
        final String mrNamespace = "http://myserver:1234/multiroom-mpd/docs/#/relations/{rel}";

        final String expected = representationFactory
                .withFlag(StandardRepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation(selfLink)
                .withNamespace("mr", mrNamespace)
                .withProperty("name", "myZone")
                .withLink("mr:player", playerLink)
                .toString(RepresentationFactory.HAL_JSON);

        final Zone zone = new Zone("myZone");
        final String actual = testSubject.newRepresentation(zone).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }
}