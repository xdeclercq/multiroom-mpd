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
    private final PlaylistSongRepresentationFactory playlistSongRepresentationFactory = new PlaylistSongRepresentationFactory(uriInfo, songRepresentationFactory);
    private final ZonePlaylistRepresentationFactory testSubject = new ZonePlaylistRepresentationFactory(uriInfo, playlistSongRepresentationFactory);

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
                .withRepresentation("mr:playlist-song",
                        playlistSongRepresentationFactory.newRepresentation(new PlaylistSong(new Song("Song A"), 1)))
                .withRepresentation("mr:playlist-song", playlistSongRepresentationFactory.newRepresentation(new PlaylistSong(new Song("Song B"), 2)))
                .toString(RepresentationFactory.HAL_JSON);

        final ZonePlaylistDto zonePlaylistDto = new ZonePlaylistDto(UUID.randomUUID(), "myZone", new ZonePlaylist(Arrays.asList(new PlaylistSong(new Song("Song A"), 1), new PlaylistSong(new Song("Song B"), 2))));

        final String actual = testSubject.newRepresentation(zonePlaylistDto).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }

}