package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.song.SongRepresentationFactory;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistSongRepresentationFactoryTest {

    private final UriInfo uriInfo = getUriInfo();
    private final SongRepresentationFactory songRepresentationFactory = new SongRepresentationFactory(uriInfo);
    private final PlaylistSongRepresentationFactory testSubject = new PlaylistSongRepresentationFactory(uriInfo, songRepresentationFactory);

    public static final String BASE_URI = "http://myserver:1234/api";

    private UriInfo getUriInfo() {
        final UriInfo result = mock(UriInfo.class);
        when(result.getBaseUri()).thenReturn(URI.create(BASE_URI));
        return result;
    }

    @Test
    public void newRepresentation() throws Exception {
        final StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();

        when(uriInfo.getBaseUriBuilder()).thenAnswer(i -> UriBuilder.fromPath(BASE_URI));
        final Song song = new Song("Song A");
        final int position = 723;
        final String expected = representationFactory
                .withFlag(RepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation()
                .withNamespace("mr", "http://myserver:1234/multiroom-mpd/docs/#/relations/{rel}")
                .withLink("mr:play", "http://myserver:1234/api/zones/myZone/playlist/723/play")
                .withProperty("position", position)
                .withRepresentation("mr:song", songRepresentationFactory.newRepresentation(song))
                .toString(RepresentationFactory.HAL_JSON);

        final PlaylistSong playlistSong = new PlaylistSong(song, position);

        final String actual = testSubject.newRepresentation("myZone", playlistSong).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }
}