package com.autelhome.multiroom.player;

import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.song.SongRepresentationFactory;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerRepresentationFactoryTest {

    private final UriInfo uriInfo = getUriInfo();
    private final PlayerStatusRepresentationFactory playerStatusRepresentationFactory = new PlayerStatusRepresentationFactory(uriInfo);
    private final SongRepresentationFactory songRepresentationFactory = new SongRepresentationFactory(uriInfo);
    private final CurrentSongRepresentationFactory currentSongRepresentationFactory = new CurrentSongRepresentationFactory(uriInfo, songRepresentationFactory);
    private final PlayerRepresentationFactory testSubject = new PlayerRepresentationFactory(uriInfo, playerStatusRepresentationFactory, currentSongRepresentationFactory);

    public static final String BASE_URI = "http://myserver:1234/api";

    private UriInfo getUriInfo() {
        final UriInfo result = mock(UriInfo.class);
        when(result.getBaseUri()).thenReturn(URI.create(BASE_URI));
        return result;
    }

    @Test
    public void newRepresentation() throws Exception {
        final StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();

        final URI self = URI.create(BASE_URI + "/zones/myZone/player");
        final URI play = URI.create(BASE_URI + "/zones/myZone/player/play");
        final URI playNext = URI.create(BASE_URI + "/zones/myZone/player/next");
        final URI pause = URI.create(BASE_URI + "/zones/myZone/player/pause");
        final URI stop = URI.create(BASE_URI + "/zones/myZone/player/stop");
        when(uriInfo.getBaseUriBuilder()).thenAnswer(i -> UriBuilder.fromPath(BASE_URI));

        final CurrentSong currentSong = new CurrentSong(new Song("Song A"), 1);

        final String expected = representationFactory
                .withFlag(RepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation(self)
                .withNamespace("mr", "http://myserver:1234/multiroom-mpd/docs/#/relations/{rel}")
                .withLink("mr:play", play)
                .withLink("mr:play-next", playNext)
                .withLink("mr:pause", pause)
                .withLink("mr:stop", stop)
                .withRepresentation("mr:status", playerStatusRepresentationFactory.newRepresentation(PlayerStatus.PAUSED, "myZone"))
                .withRepresentation("mr:current-song", currentSongRepresentationFactory.newRepresentation(currentSong))
                .toString(RepresentationFactory.HAL_JSON);

        final PlayerDto playerDto = new PlayerDto(UUID.randomUUID(), "myZone", PlayerStatus.PAUSED, currentSong);

        final String actual = testSubject.newRepresentation(playerDto).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }
}