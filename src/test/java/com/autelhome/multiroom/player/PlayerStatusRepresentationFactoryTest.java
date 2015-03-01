package com.autelhome.multiroom.player;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Test;

import java.net.URI;

import static org.fest.assertions.api.Assertions.assertThat;

public class PlayerStatusRepresentationFactoryTest {

    private final URI requestURI = URI.create("ws://server:666/multiroom-mpd/ws/zones/zone1/player/status");
    private final PlayerStatusRepresentationFactory testSubject = new PlayerStatusRepresentationFactory(requestURI);

    @Test
    public void newRepresentation() throws Exception {

        final String mrNamespace = "http://server:666/multiroom-mpd/docs/#/relations/{rel}";

        final StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();

        final PlayerStatus playerStatus = PlayerStatus.PAUSED;

        final String expected = representationFactory
                .withFlag(StandardRepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation(requestURI)
                .withNamespace("mr", mrNamespace)
                .withProperty("status", playerStatus.toString())
                .toString(RepresentationFactory.HAL_JSON);

        final String actual = testSubject.newRepresentation(playerStatus, "zone1").toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }
}