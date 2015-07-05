package com.autelhome.multiroom.player;

import com.autelhome.multiroom.hal.HalJsonMessageBodyWriter;
import com.autelhome.multiroom.playlist.ZonePlaylistResourceFactory;
import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.*;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class PlayerResourceTest {

    private static final String BASE_URI = "http://server:3421/multiroom-mpd/api/";
    private static final String KITCHEN = "Kitchen";
    private static final ZoneDto KITCHEN_DTO = new ZoneDto(UUID.randomUUID(), KITCHEN, 7912, 1);

    private static final ZoneDto BATHROOM_DTO = new ZoneDto(UUID.randomUUID(), "Bathroom", 7912, 1);

    private static final String PLAYER_JSON_FILE_NAME = "player.json";
    private final ZoneService zoneService = mock(ZoneService.class);
    private final UriInfo uriInfo = getUriInfo();
    private final ZoneRepresentationFactory zoneRepresentationFactory = new ZoneRepresentationFactory(uriInfo);
    private final ZonesRepresentationFactory zonesRepresentationFactory = new ZonesRepresentationFactory(uriInfo, zoneRepresentationFactory);
    private final PlayerResourceFactory playerResourceFactory = mock(PlayerResourceFactory.class);
    private final EventBus eventBus = mock(EventBus.class);
    private final PlayerStatusRepresentationFactory playerStatusRepresentationFactory = new PlayerStatusRepresentationFactory(uriInfo);
    private final PlayerRepresentationFactory playerRepresentationFactory = new PlayerRepresentationFactory(uriInfo, playerStatusRepresentationFactory);
    private final PlayerService playerService = mock(PlayerService.class);
    private final ZonePlaylistResourceFactory zonePlaylistResourceFactory = mock(ZonePlaylistResourceFactory.class);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ZonesResource(zoneService, zonesRepresentationFactory, zoneRepresentationFactory, playerResourceFactory, zonePlaylistResourceFactory, eventBus))
            .addProvider(HalJsonMessageBodyWriter.class)
            .build();

    private UriInfo getUriInfo() {
        final UriInfo result = mock(UriInfo.class);
        when(result.getBaseUri()).thenReturn(URI.create(BASE_URI));
        return result;
    }

    @Test
    public void getPlayer() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final ZoneDto kitchen = new ZoneDto(zoneId, KITCHEN, 789, 1);

        when(zoneService.getByName(KITCHEN)).thenReturn(Optional.of(kitchen));
        when(playerService.getPlayerByZoneName(KITCHEN)).thenReturn(Optional.of(new PlayerDto(zoneId, KITCHEN, PlayerStatus.PAUSED)));
        when(playerResourceFactory.newInstance(kitchen)).thenReturn(new PlayerResource(kitchen, playerService, playerRepresentationFactory, eventBus));

        final Response actualResponse = resources.client().target("/zones/Kitchen/player").request().accept(RepresentationFactory.HAL_JSON).get();

        final String expectedContent = Resources.toString(Resources.getResource(getClass(), PLAYER_JSON_FILE_NAME), Charsets.UTF_8);
        final String actualContent = actualResponse.readEntity(String.class);
        
        assertEquals(expectedContent, actualContent, true);

        final int actualStatus = actualResponse.getStatus();

        assertThat(actualStatus).isEqualTo(200);

    }

    @Test
    public void play() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final ZoneDto kitchen = new ZoneDto(zoneId, KITCHEN, 789, 1);

        when(zoneService.getByName(KITCHEN)).thenReturn(Optional.of(kitchen));
        when(playerService.getPlayerByZoneName(KITCHEN)).thenReturn(Optional.of(new PlayerDto(zoneId, KITCHEN, PlayerStatus.PAUSED)));
        when(playerResourceFactory.newInstance(kitchen)).thenReturn(new PlayerResource(kitchen, playerService, playerRepresentationFactory, eventBus));

        final Response actualResponse = resources.client().target("/zones/Kitchen/player/play").request().accept(RepresentationFactory.HAL_JSON).post(null);

        final String expectedContent = Resources.toString(Resources.getResource(getClass(), PLAYER_JSON_FILE_NAME), Charsets.UTF_8);
        final String actualContent = actualResponse.readEntity(String.class);

        assertEquals(expectedContent, actualContent, true);

        final int actualStatus = actualResponse.getStatus();
        assertThat(actualStatus).isEqualTo(202);
    }

    @Test(expected = ProcessingException.class)
    public void playWithUnknownPlayer() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final ZoneDto kitchen = new ZoneDto(zoneId, KITCHEN, 789, 1);

        when(zoneService.getByName(KITCHEN)).thenReturn(Optional.of(kitchen));
        when(playerService.getPlayerByZoneName(KITCHEN)).thenReturn(Optional.empty());
        when(playerResourceFactory.newInstance(kitchen)).thenReturn(new PlayerResource(kitchen, playerService, playerRepresentationFactory, eventBus));

        resources.client().target("/zones/Kitchen/player/play").request().accept(RepresentationFactory.HAL_JSON).post(null);
    }



    @Test
    public void pause() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final ZoneDto kitchen = new ZoneDto(zoneId, KITCHEN, 789, 1);

        when(zoneService.getByName(KITCHEN)).thenReturn(Optional.of(kitchen));
        when(playerService.getPlayerByZoneName(KITCHEN)).thenReturn(Optional.of(new PlayerDto(zoneId, KITCHEN, PlayerStatus.PAUSED)));
        when(playerResourceFactory.newInstance(kitchen)).thenReturn(new PlayerResource(kitchen, playerService, playerRepresentationFactory, eventBus));

        final Response actualResponse = resources.client().target("/zones/Kitchen/player/pause").request().accept(RepresentationFactory.HAL_JSON).post(null);

        final String expectedContent = Resources.toString(Resources.getResource(getClass(), PLAYER_JSON_FILE_NAME), Charsets.UTF_8);
        final String actualContent = actualResponse.readEntity(String.class);

        assertEquals(expectedContent, actualContent, true);

        final int actualStatus = actualResponse.getStatus();
        assertThat(actualStatus).isEqualTo(202);
    }

    @Test
    public void stop() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final ZoneDto kitchen = new ZoneDto(zoneId, KITCHEN, 789, 1);

        when(zoneService.getByName(KITCHEN)).thenReturn(Optional.of(kitchen));
        when(playerService.getPlayerByZoneName(KITCHEN)).thenReturn(Optional.of(new PlayerDto(zoneId, KITCHEN, PlayerStatus.PAUSED)));
        when(playerResourceFactory.newInstance(kitchen)).thenReturn(new PlayerResource(kitchen, playerService, playerRepresentationFactory, eventBus));

        final Response actualResponse = resources.client().target("/zones/Kitchen/player/stop").request().accept(RepresentationFactory.HAL_JSON).post(null);

        final String expectedContent = Resources.toString(Resources.getResource(getClass(), PLAYER_JSON_FILE_NAME), Charsets.UTF_8);
        final String actualContent = actualResponse.readEntity(String.class);

        assertEquals(expectedContent, actualContent, true);

        final int actualStatus = actualResponse.getStatus();
        assertThat(actualStatus).isEqualTo(202);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final PlayerResource playerResource1 = new PlayerResource(KITCHEN_DTO, null, null, null);
        final PlayerResource playerResource2 = new PlayerResource(KITCHEN_DTO, null, null, null);
        final PlayerResource playerResource3 = new PlayerResource(null, null, null, null);
        final PlayerResource playerResource4 = new PlayerResource(null, null, null, null);

        assertThat(playerResource1).isEqualTo(playerResource1);
        assertThat(playerResource1).isEqualTo(playerResource2);
        assertThat(playerResource3).isEqualTo(playerResource4);
    }

    @Test
    @SuppressFBWarnings("EC_UNRELATED_TYPES")
    public void shouldNotBeEqual() throws Exception {
        final PlayerResource playerResource1 = new PlayerResource(KITCHEN_DTO, null, null, null);
        final PlayerResource playerResource2 = new PlayerResource(BATHROOM_DTO, null, null, null);
        final PlayerResource playerResource3 = new PlayerResource(null, null, null, null);

        assertThat(playerResource1).isNotEqualTo(playerResource2);
        assertThat(playerResource1).isNotEqualTo(playerResource3);
        assertThat(playerResource1.equals(null)).isFalse(); // NOPMD
        assertThat(playerResource1.equals(" ")).isFalse(); // NOPMD
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int hashCode1 = new PlayerResource(KITCHEN_DTO, null, null, null).hashCode();
        final int hashCode2 = new PlayerResource(KITCHEN_DTO, null, null, null).hashCode();
        final int hashCode3 = new PlayerResource(null, null, null, null).hashCode();
        final int hashCode4 = new PlayerResource(null, null, null, null).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
        assertThat(hashCode3).isEqualTo(hashCode4);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int hashCode1 = new PlayerResource(KITCHEN_DTO, null, null, null).hashCode();
        final int hashCode2 = new PlayerResource(BATHROOM_DTO, null, null, null).hashCode();
        final int hashCode3 = new PlayerResource(null, null, null, null).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
        assertThat(hashCode2).isNotEqualTo(hashCode3);
    }
}