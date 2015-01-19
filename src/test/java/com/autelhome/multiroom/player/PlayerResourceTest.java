package com.autelhome.multiroom.player;

import com.autelhome.multiroom.hal.HalJsonMessageBodyWriter;
import com.autelhome.multiroom.hal.MultiroomNamespaceResolver;
import com.autelhome.multiroom.zone.*;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class PlayerResourceTest {

    private static final String KITCHEN = "Kitchen";
    private static final String BATHROOM = "Bathroom";

    private final ZoneService zoneService = mock(ZoneService.class);
    private final UriInfo uriInfo = mock(UriInfo.class);
    private final MultiroomNamespaceResolver multiroomNamespaceResolver = mock(MultiroomNamespaceResolver.class);
    private final ZoneRepresentationFactory zoneRepresentationFactory = new ZoneRepresentationFactory(uriInfo, multiroomNamespaceResolver);
    private final ZonesRepresentationFactory zonesRepresentationFactory = new ZonesRepresentationFactory(uriInfo, zoneRepresentationFactory, multiroomNamespaceResolver);
    private final PlayerResourceFactory playerResourceFactory = mock(PlayerResourceFactory.class);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ZoneResource(zoneService, zonesRepresentationFactory, zoneRepresentationFactory, playerResourceFactory))
            .addProvider(HalJsonMessageBodyWriter.class)
            .build();

    @Test
    public void getPlayer() throws Exception {
        final String expected = Resources.toString(Resources.getResource(getClass(), "player.json"), Charsets.UTF_8);

        final Zone kitchen = new Zone("Kitchen");

        final UriBuilder selfUriBuilder = UriBuilder.fromPath("/");
        final UriBuilder mrNamespaceUriBuilder = UriBuilder.fromPath("/");
        when(uriInfo.getBaseUriBuilder()).thenReturn(selfUriBuilder, mrNamespaceUriBuilder);
        when(multiroomNamespaceResolver.resolve()).thenReturn("/docs/rels/{rel}");
        final PlayerRepresentationFactory playerRepresentationFactory = new PlayerRepresentationFactory(uriInfo);
        when(zoneService.getByName("Kitchen")).thenReturn(kitchen);
        when(playerResourceFactory.newInstance(kitchen)).thenReturn(new PlayerResource(new Player(kitchen, PlayerStatus.PLAYING), playerRepresentationFactory));

        final String actual = resources.client().resource("/zones/Kitchen/player").accept(RepresentationFactory.HAL_JSON).get(String.class);

        assertEquals(expected, actual, true);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final PlayerResource playerResource1 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.PLAYING), null);
        final PlayerResource playerResource2 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.PLAYING), null);
        final PlayerResource playerResource3 = new PlayerResource(null, null);
        final PlayerResource playerResource4 = new PlayerResource(null, null);

        assertThat(playerResource1).isEqualTo(playerResource1);
        assertThat(playerResource1).isEqualTo(playerResource2);
        assertThat(playerResource3).isEqualTo(playerResource4);
    }

    @Test
    @SuppressFBWarnings("EC_UNRELATED_TYPES")
    public void shouldNotBeEqual() throws Exception {
        final PlayerResource playerResource1 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.PLAYING), null);
        final PlayerResource playerResource2 = new PlayerResource(new Player(new Zone(BATHROOM), PlayerStatus.PLAYING), null);
        final PlayerResource playerResource3 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.STOPPED), null);
        final PlayerResource playerResource4 = new PlayerResource(null, null);

        assertThat(playerResource1).isNotEqualTo(playerResource2);
        assertThat(playerResource1).isNotEqualTo(playerResource3);
        assertThat(playerResource3).isNotEqualTo(playerResource4);
        assertThat(playerResource1.equals(null)).isFalse(); // NOPMD
        assertThat(playerResource1.equals(" ")).isFalse(); // NOPMD
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int hashCode1 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.PLAYING), null).hashCode();
        final int hashCode2 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.PLAYING), null).hashCode();
        final int hashCode3 = new PlayerResource(null, null).hashCode();
        final int hashCode4 = new PlayerResource(null, null).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
        assertThat(hashCode3).isEqualTo(hashCode4);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int hashCode1 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.PLAYING), null).hashCode();
        final int hashCode2 = new PlayerResource(new Player(new Zone(BATHROOM), PlayerStatus.PLAYING), null).hashCode();
        final int hashCode3 = new PlayerResource(new Player(new Zone(KITCHEN), PlayerStatus.STOPPED), null).hashCode();
        final int hashCode4 = new PlayerResource(null, null).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
        assertThat(hashCode2).isNotEqualTo(hashCode3);
        assertThat(hashCode3).isNotEqualTo(hashCode4);
    }
}