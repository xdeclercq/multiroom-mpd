package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.song.SongRepresentationFactory;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZonePlaylistRepresentationFactoryTest {
    private final UriInfo uriInfo = getUriInfo();
    private final SongRepresentationFactory songRepresentationFactory = new SongRepresentationFactory(uriInfo);
    private final ZonePlaylistRepresentationFactory testSubject = new ZonePlaylistRepresentationFactory(uriInfo, songRepresentationFactory);

    public static final String BASE_URI = "http://myserver:1234/api";

    private UriInfo getUriInfo() {
        final UriInfo result = mock(UriInfo.class);
        when(result.getBaseUri()).thenReturn(URI.create(BASE_URI));
        return result;
    }

    @Test
    public void newRepresentation() throws Exception {
        final StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();

        final URI self = URI.create(BASE_URI + "/zones/myZone/playlist");
        when(uriInfo.getBaseUriBuilder()).thenAnswer(i -> UriBuilder.fromPath(BASE_URI));
        final String expected = representationFactory
                .withFlag(RepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation(self)
                .withNamespace("mr", "http://myserver:1234/multiroom-mpd/docs/#/relations/{rel}")
                .withRepresentation("mr:song", songRepresentationFactory.newRepresentation(new Song("Song A")))
                .withRepresentation("mr:song", songRepresentationFactory.newRepresentation(new Song("Song B")))
                .toString(RepresentationFactory.HAL_JSON);

        final ZonePlaylistDto zonePlaylistDto = new ZonePlaylistDto(UUID.randomUUID(), "myZone", new ZonePlaylist(Arrays.asList(new Song("Song A"), new Song("Song B"))));

        final String actual = testSubject.newRepresentation(zonePlaylistDto).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }

}