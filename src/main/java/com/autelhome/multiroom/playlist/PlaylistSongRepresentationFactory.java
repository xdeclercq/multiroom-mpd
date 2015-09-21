package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.autelhome.multiroom.song.SongRepresentationFactory;
import com.autelhome.multiroom.zone.ZonesResource;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import java.net.URI;
import javax.ws.rs.core.UriInfo;

/**
 * {@link com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory} for a {@link com.autelhome.multiroom.zone.Zone}.
 *
 * @author xavier
 */
public class PlaylistSongRepresentationFactory extends BaseRepresentationFactory {

    private final SongRepresentationFactory songRepresentationFactory;

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     * @param songRepresentationFactory a {@link SongRepresentationFactory} instance
     */
    @Inject
    public PlaylistSongRepresentationFactory(final UriInfo uriInfo, final SongRepresentationFactory songRepresentationFactory) {
        super(uriInfo);
        this.songRepresentationFactory = songRepresentationFactory;
    }

    /**
     * Returns a new {@link Representation} of a playlist song.
     *
     * @param playlistSong a playlist song
     * @param zoneName a zone name
     * @return a new {@link Representation} of the playlist song
     */
    public Representation newRepresentation(final String zoneName, final PlaylistSong playlistSong) {

        final URI play = getBaseURIBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, "getZonePlaylistResource")
                .path(ZonePlaylistResource.class, "play")
                .build(zoneName, playlistSong.getPosition());

        return newRepresentation()
                .withNamespace("mr", getMRNamespace())
                .withLink("mr:play", play)
                .withRepresentation("mr:song", songRepresentationFactory.newRepresentation(playlistSong.getSong()))
                .withProperty("position", playlistSong.getPosition());
    }

}
