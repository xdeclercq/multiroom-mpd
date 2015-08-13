package com.autelhome.multiroom.player;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.autelhome.multiroom.song.SongRepresentationFactory;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;

import javax.ws.rs.core.UriInfo;

/**
 * {@link BaseRepresentationFactory} for a {@link CurrentSong}.
 *
 * @author xavier
 */
public class CurrentSongRepresentationFactory extends BaseRepresentationFactory
{

    private final SongRepresentationFactory songRepresentationFactory;

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     * @param songRepresentationFactory a {@link SongRepresentationFactory} instance
     */
    @Inject
    public CurrentSongRepresentationFactory(final UriInfo uriInfo, final SongRepresentationFactory songRepresentationFactory) {
        super(uriInfo);
        this.songRepresentationFactory = songRepresentationFactory;
    }

    /**
     * Returns a new {@link Representation} of a song.
     *
     * @param currentSong a current song
     * @return a new {@link Representation} of the current song
     */
    public Representation newRepresentation(final CurrentSong currentSong) {

        return newRepresentation()
                .withNamespace("mr", getMRNamespace())
                .withRepresentation("mr:song", songRepresentationFactory.newRepresentation(currentSong.getSong()))
                .withProperty("position", currentSong.getPosition());
    }

}
