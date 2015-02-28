package com.autelhome.multiroom.zone;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Test;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZoneRepresentationFactoryTest {

    private final UriInfo uriInfo = getUriInfo();
    private final ZoneRepresentationFactory testSubject = new ZoneRepresentationFactory(uriInfo);
    public static final String BASE_URI = "http://myserver:1234/api";


    private UriInfo getUriInfo() {
        final UriInfo result = mock(UriInfo.class);
        when(result.getBaseUri()).thenReturn(URI.create(BASE_URI));
        return result;
    }

    @Test
    public void newRepresentation() throws Exception {
        final StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();


        final String selfLink = BASE_URI + "/zones/myZone";
        final String playerLink = selfLink + "/player";

        final String mrNamespace = "http://myserver:1234/multiroom-mpd/docs/#/relations/{rel}";

        final String expected = representationFactory
                .withFlag(StandardRepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation(selfLink)
                .withNamespace("mr", mrNamespace)
                .withProperty("name", "myZone")
                .withProperty("mpdInstancePort", 7912)
                .withLink("mr:player", playerLink)
                .toString(RepresentationFactory.HAL_JSON);

        final ZoneDto zone = new ZoneDto(UUID.randomUUID(), "myZone", 7912, 1);
        final String actual = testSubject.newRepresentation(zone).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }
}