package com.autelhome.multiroom.song;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SongRepresentationFactoryTest {

    private final UriInfo uriInfo = getUriInfo();
    private final SongRepresentationFactory testSubject = new SongRepresentationFactory(uriInfo);

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
        final String title = "Song A";
        final String expected = representationFactory
                .withFlag(RepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation()
                .withProperty("title", title)
                .toString(RepresentationFactory.HAL_JSON);

        final Song song = new Song(title);

        final String actual = testSubject.newRepresentation(song).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }}