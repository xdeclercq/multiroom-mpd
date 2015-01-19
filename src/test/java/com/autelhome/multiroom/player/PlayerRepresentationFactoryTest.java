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

        final String baseURI = "http://myserver/api";
        final URI self = URI.create(baseURI + "/zones/myZone/player");
        when(uriInfo.getBaseUriBuilder()).thenReturn(UriBuilder.fromUri(baseURI));
        final String expected = representationFactory
                .newRepresentation(self)
                .toString(RepresentationFactory.HAL_JSON);


        final Zone zone = new Zone("myZone");
        final Player player = new Player(zone, PlayerStatus.PLAYING);
        final String actual = testSubject.newRepresentation(player).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }
}