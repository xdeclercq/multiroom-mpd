package com.autelhome.multiroom.player;

import com.autelhome.multiroom.zone.Zone;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerRepresentationFactoryTest {

    private final UriInfo uriInfo = mock(UriInfo.class);
    private final PlayerRepresentationFactory testSubject = new PlayerRepresentationFactory(uriInfo);

    @Test
    public void newRepresentation() throws Exception {
        final StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();

        final String baseURI = "http://myserver:1234/api";
        when(uriInfo.getBaseUri()).thenReturn(URI.create(baseURI));
        final URI self = URI.create(baseURI + "/zones/myZone/player");
        final URI play = URI.create(baseURI + "/zones/myZone/player/play");
        final URI stop = URI.create(baseURI + "/zones/myZone/player/stop");
        when(uriInfo.getBaseUriBuilder()).thenAnswer(i -> UriBuilder.fromPath(baseURI));
        final String expected = representationFactory
                .withFlag(RepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation(self)
                .withNamespace("mr", "http://myserver:1234/multiroom-mpd/docs/#/relations/{rel}")
                .withLink("mr:play", play)
                .withLink("mr:stop", stop)
                .toString(RepresentationFactory.HAL_JSON);


        final Zone zone = new Zone("myZone");
        final Player player = new Player(zone, PlayerStatus.PLAYING);
        final String actual = testSubject.newRepresentation(player).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }
}