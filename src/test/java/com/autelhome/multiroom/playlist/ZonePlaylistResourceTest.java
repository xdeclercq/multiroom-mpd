package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.hal.HalJsonMessageBodyWriter;
import com.autelhome.multiroom.player.*;
import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.song.SongRepresentationFactory;
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
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class ZonePlaylistResourceTest {

    private static final String BASE_URI = "http://server:3421/multiroom-mpd/api/";
    private static final String KITCHEN = "Kitchen";
    private static final ZoneDto KITCHEN_DTO = new ZoneDto(UUID.randomUUID(), KITCHEN, 7912, 1);

    private static final ZoneDto BATHROOM_DTO = new ZoneDto(UUID.randomUUID(), "Bathroom", 7912, 1);

    private static final String PLAYLIST_JSON_FILE_NAME = "zone_playlist.json";
    private final ZoneService zoneService = mock(ZoneService.class);
    private final UriInfo uriInfo = getUriInfo();
    private final ZoneRepresentationFactory zoneRepresentationFactory = new ZoneRepresentationFactory(uriInfo);
    private final ZonesRepresentationFactory zonesRepresentationFactory = new ZonesRepresentationFactory(uriInfo, zoneRepresentationFactory);
    private final PlayerResourceFactory playerResourceFactory = mock(PlayerResourceFactory.class);
    private final EventBus eventBus = mock(EventBus.class);
    private final SongRepresentationFactory songRepresentationFactory = new SongRepresentationFactory(uriInfo);
    private final ZonePlaylistRepresentationFactory  zonePlaylistRepresentationFactory = new ZonePlaylistRepresentationFactory(uriInfo, songRepresentationFactory);
    private final ZonePlaylistService zonePlaylistService= mock(ZonePlaylistService.class);
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
    public void getPlaylist() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final ZoneDto kitchen = new ZoneDto(zoneId, KITCHEN, 789, 1);

        when(zoneService.getByName(KITCHEN)).thenReturn(Optional.of(kitchen));
        final ZonePlaylist playlist = new ZonePlaylist(Arrays.asList(new Song("song A"), new Song("song B")));
        when(zonePlaylistService.getPlaylistByZoneName(KITCHEN)).thenReturn(Optional.of(new ZonePlaylistDto(zoneId, KITCHEN, playlist)));
        when(zonePlaylistResourceFactory.newInstance(kitchen)).thenReturn(new ZonePlaylistResource(kitchen, zonePlaylistService, zonePlaylistRepresentationFactory));

        final Response actualResponse = resources.client().target("/zones/Kitchen/playlist").request().accept(RepresentationFactory.HAL_JSON).get();

        final String expectedContent = Resources.toString(Resources.getResource(getClass(), PLAYLIST_JSON_FILE_NAME), Charsets.UTF_8);
        final String actualContent = actualResponse.readEntity(String.class);

        assertEquals(expectedContent, actualContent, true);

        final int actualStatus = actualResponse.getStatus();

        assertThat(actualStatus).isEqualTo(200);
    }

    @Test(expected = ProcessingException.class)
    public void getPlaylistWithUnknownPlaylist() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final ZoneDto kitchen = new ZoneDto(zoneId, KITCHEN, 789, 1);

        when(zoneService.getByName(KITCHEN)).thenReturn(Optional.of(kitchen));
        when(zonePlaylistService.getPlaylistByZoneName(KITCHEN)).thenReturn(Optional.empty());
        when(zonePlaylistResourceFactory.newInstance(kitchen)).thenReturn(new ZonePlaylistResource(kitchen, zonePlaylistService, zonePlaylistRepresentationFactory));

        resources.client().target("/zones/Kitchen/playlist").request().accept(RepresentationFactory.HAL_JSON).get();
    }


    @Test
    public void shouldBeEqual() throws Exception {
        final ZonePlaylistResource zonePlaylistResource1 = new ZonePlaylistResource(KITCHEN_DTO, null, null);
        final ZonePlaylistResource zonePlaylistResource2 = new ZonePlaylistResource(KITCHEN_DTO, null, null);
        final ZonePlaylistResource zonePlaylistResource3 = new ZonePlaylistResource(null, null, null);
        final ZonePlaylistResource zonePlaylistResource4 = new ZonePlaylistResource(null, null, null);

        assertThat(zonePlaylistResource1).isEqualTo(zonePlaylistResource1);
        assertThat(zonePlaylistResource1).isEqualTo(zonePlaylistResource2);
        assertThat(zonePlaylistResource3).isEqualTo(zonePlaylistResource4);
    }

    @Test
    @SuppressFBWarnings("EC_UNRELATED_TYPES")
    public void shouldNotBeEqual() throws Exception {
        final ZonePlaylistResource zonePlaylistResource1 = new ZonePlaylistResource(KITCHEN_DTO, null, null);
        final ZonePlaylistResource zonePlaylistResource2 = new ZonePlaylistResource(BATHROOM_DTO, null, null);
        final ZonePlaylistResource zonePlaylistResource3 = new ZonePlaylistResource(null, null, null);

        assertThat(zonePlaylistResource1).isNotEqualTo(zonePlaylistResource2);
        assertThat(zonePlaylistResource1).isNotEqualTo(zonePlaylistResource3);
        assertThat(zonePlaylistResource1.equals(null)).isFalse(); // NOPMD
        assertThat(zonePlaylistResource1.equals(" ")).isFalse(); // NOPMD
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int hashCode1 = new ZonePlaylistResource(KITCHEN_DTO, null, null).hashCode();
        final int hashCode2 = new ZonePlaylistResource(KITCHEN_DTO, null, null).hashCode();
        final int hashCode3 = new ZonePlaylistResource(null, null, null).hashCode();
        final int hashCode4 = new ZonePlaylistResource(null, null, null).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
        assertThat(hashCode3).isEqualTo(hashCode4);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int hashCode1 = new ZonePlaylistResource(KITCHEN_DTO, null, null).hashCode();
        final int hashCode2 = new ZonePlaylistResource(BATHROOM_DTO, null, null).hashCode();
        final int hashCode3 = new ZonePlaylistResource(null, null, null).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
        assertThat(hashCode2).isNotEqualTo(hashCode3);
    }
}