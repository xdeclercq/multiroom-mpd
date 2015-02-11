package com.autelhome.multiroom.player;

import com.autelhome.multiroom.hal.HalJsonMessageBodyWriter;
import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.*;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.ClientResponse;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class PlayerResourceTest {

    private static final String BASE_URI = "http://server:3421/api/";
    private static final String KITCHEN = "Kitchen";

    private static final String BATHROOM = "Bathroom";
    private static final String PLAYER_JSON_FILE_NAME = "player.json";
    private final ZoneService zoneService = mock(ZoneService.class);
    private final UriInfo uriInfo = mock(UriInfo.class);
    private final ZoneRepresentationFactory zoneRepresentationFactory = new ZoneRepresentationFactory(uriInfo);
    private final ZonesRepresentationFactory zonesRepresentationFactory = new ZonesRepresentationFactory(uriInfo, zoneRepresentationFactory);
    private final PlayerResourceFactory playerResourceFactory = mock(PlayerResourceFactory.class);

    private final EventBus eventBus = mock(EventBus.class);
    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ZonesResource(zoneService, zonesRepresentationFactory, zoneRepresentationFactory, playerResourceFactory))
            .addProvider(HalJsonMessageBodyWriter.class)
            .build();

    @Test
    public void getPlayer() throws Exception {
        final Zone kitchen = new Zone(KITCHEN);

        when(uriInfo.getBaseUriBuilder()).thenAnswer(i -> UriBuilder.fromPath(BASE_URI));
        when(uriInfo.getBaseUri()).thenReturn(URI.create(BASE_URI));
        final PlayerRepresentationFactory playerRepresentationFactory = new PlayerRepresentationFactory(uriInfo);
        when(zoneService.getByName(KITCHEN)).thenReturn(kitchen);
        when(playerResourceFactory.newInstance(kitchen)).thenReturn(new PlayerResource(new Player(kitchen, PlayerStatus.PLAYING), playerRepresentationFactory, eventBus));

        final ClientResponse actualResponse = resources.client().resource("/zones/Kitchen/player").accept(RepresentationFactory.HAL_JSON).get(ClientResponse.class);

        final String expectedContent = Resources.toString(Resources.getResource(getClass(), PLAYER_JSON_FILE_NAME), Charsets.UTF_8);
        final String actualContent = actualResponse.getEntity(String.class);
        
        assertEquals(expectedContent, actualContent, true);

        final int actualStatus = actualResponse.getStatus();

        assertThat(actualStatus).isEqualTo(200);

    }

    @Test
    public void play() throws Exception {
        final Zone kitchen = new Zone(KITCHEN);

        when(uriInfo.getBaseUriBuilder()).thenAnswer(i -> UriBuilder.fromPath(BASE_URI));
        when(uriInfo.getBaseUri()).thenReturn(URI.create(BASE_URI));
        final PlayerRepresentationFactory playerRepresentationFactory = new PlayerRepresentationFactory(uriInfo);
        when(zoneService.getByName(KITCHEN)).thenReturn(kitchen);
        when(playerResourceFactory.newInstance(kitchen)).thenReturn(new PlayerResource(new Player(kitchen, PlayerStatus.PLAYING), playerRepresentationFactory, eventBus));

        final ClientResponse actualResponse = resources.client().resource("/zones/Kitchen/player/play").accept(RepresentationFactory.HAL_JSON).post(ClientResponse.class);

        final String expectedContent = Resources.toString(Resources.getResource(getClass(), PLAYER_JSON_FILE_NAME), Charsets.UTF_8);
        final String actualContent = actualResponse.getEntity(String.class);

        assertEquals(expectedContent, actualContent, true);

        final int actualStatus = actualResponse.getStatus();
        assertThat(actualStatus).isEqualTo(202);
    }

    @Test
    public void pause() throws Exception {
        final Zone kitchen = new Zone(KITCHEN);

        when(uriInfo.getBaseUriBuilder()).thenAnswer(i -> UriBuilder.fromPath(BASE_URI));
        when(uriInfo.getBaseUri()).thenReturn(URI.create(BASE_URI));
        final PlayerRepresentationFactory playerRepresentationFactory = new PlayerRepresentationFactory(uriInfo);
        when(zoneService.getByName(KITCHEN)).thenReturn(kitchen);
        when(playerResourceFactory.newInstance(kitchen)).thenReturn(new PlayerResource(new Player(kitchen, PlayerStatus.PLAYING), playerRepresentationFactory, eventBus));

        final ClientResponse actualResponse = resources.client().resource("/zones/Kitchen/player/pause").accept(RepresentationFactory.HAL_JSON).post(ClientResponse.class);

        final String expectedContent = Resources.toString(Resources.getResource(getClass(), PLAYER_JSON_FILE_NAME), Charsets.UTF_8);
        final String actualContent = actualResponse.getEntity(String.class);

        assertEquals(expectedContent, actualContent, true);

        final int actualStatus = actualResponse.getStatus();
        assertThat(actualStatus).isEqualTo(202);
    }

    @Test
    public void stop() throws Exception {
        final Zone kitchen = new Zone(KITCHEN);

        when(uriInfo.getBaseUriBuilder()).thenAnswer(i -> UriBuilder.fromPath(BASE_URI));
        when(uriInfo.getBaseUri()).thenReturn(URI.create(BASE_URI));
        final PlayerRepresentationFactory playerRepresentationFactory = new PlayerRepresentationFactory(uriInfo);
        when(zoneService.getByName(KITCHEN)).thenReturn(kitchen);
        when(playerResourceFactory.newInstance(kitchen)).thenReturn(new PlayerResource(new Player(kitchen, PlayerStatus.PLAYING), playerRepresentationFactory, eventBus));

        final ClientResponse actualResponse = resources.client().resource("/zones/Kitchen/player/stop").accept(RepresentationFactory.HAL_JSON).post(ClientResponse.class);

        final String expectedContent = Resources.toString(Resources.getResource(getClass(), PLAYER_JSON_FILE_NAME), Charsets.UTF_8);
        final String actualContent = actualResponse.getEntity(String.class);

        assertEquals(expectedContent, actualContent, true);

        final int actualStatus = actualResponse.getStatus();
        assertThat(actualStatus).isEqualTo(202);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final PlayerResource playerResource1 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.PLAYING), null, null);
        final PlayerResource playerResource2 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.PLAYING), null, null);
        final PlayerResource playerResource3 = new PlayerResource(null, null, null);
        final PlayerResource playerResource4 = new PlayerResource(null, null, null);

        assertThat(playerResource1).isEqualTo(playerResource1);
        assertThat(playerResource1).isEqualTo(playerResource2);
        assertThat(playerResource3).isEqualTo(playerResource4);
    }

    @Test
    @SuppressFBWarnings("EC_UNRELATED_TYPES")
    public void shouldNotBeEqual() throws Exception {
        final PlayerResource playerResource1 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.PLAYING), null, null);
        final PlayerResource playerResource2 = new PlayerResource(new Player(new Zone(BATHROOM), PlayerStatus.PLAYING), null, null);
        final PlayerResource playerResource3 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.STOPPED), null, null);
        final PlayerResource playerResource4 = new PlayerResource(null, null, null);

        assertThat(playerResource1).isNotEqualTo(playerResource2);
        assertThat(playerResource1).isNotEqualTo(playerResource3);
        assertThat(playerResource3).isNotEqualTo(playerResource4);
        assertThat(playerResource1.equals(null)).isFalse(); // NOPMD
        assertThat(playerResource1.equals(" ")).isFalse(); // NOPMD
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int hashCode1 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.PLAYING), null, null).hashCode();
        final int hashCode2 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.PLAYING), null, null).hashCode();
        final int hashCode3 = new PlayerResource(null, null, null).hashCode();
        final int hashCode4 = new PlayerResource(null, null, null).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
        assertThat(hashCode3).isEqualTo(hashCode4);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int hashCode1 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.PLAYING), null, null).hashCode();
        final int hashCode2 = new PlayerResource(new Player(new Zone(BATHROOM), PlayerStatus.PLAYING), null, null).hashCode();
        final int hashCode3 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.STOPPED), null, null).hashCode();
        final int hashCode4 = new PlayerResource(null, null, null).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
        assertThat(hashCode2).isNotEqualTo(hashCode3);
        assertThat(hashCode3).isNotEqualTo(hashCode4);
    }
}