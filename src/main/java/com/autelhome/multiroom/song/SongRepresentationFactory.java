package com.autelhome.multiroom.song;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import javax.ws.rs.core.UriInfo;

/**
 * {@link com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory} for a {@link com.autelhome.multiroom.zone.Zone}.
 *
 * @author xavier
 */
public class SongRepresentationFactory extends BaseRepresentationFactory {

    /**
     * Constructor.
     *
     * @param uriInfo the {@link javax.ws.rs.core.UriInfo} related to the request
     */
    @Inject
    public SongRepresentationFactory(final UriInfo uriInfo) {
        super(uriInfo);
    }

    /**
     * Returns a new {@link Representation} of a song.
     *
     * @param song a song
     * @return a new {@link Representation} of the song
     */
    public Representation newRepresentation(final Song song) {
        return newRepresentation()
                .withProperty("title", song.getTitle());
    }

}
