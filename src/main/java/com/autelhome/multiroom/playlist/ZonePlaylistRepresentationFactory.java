package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.autelhome.multiroom.zone.ZonesResource;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;

/**
 * {@link BaseRepresentationFactory} for a {@link ZonePlaylistDto}.
 *
 * @author xdeclercq
 */
public class ZonePlaylistRepresentationFactory extends BaseRepresentationFactory
{

    private final PlaylistSongRepresentationFactory playlistSongRepresentationFactory;
    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     * @param playlistSongRepresentationFactory a {@link PlaylistSongRepresentationFactory} instance
     */
    @Inject
    public ZonePlaylistRepresentationFactory(final UriInfo uriInfo, final PlaylistSongRepresentationFactory playlistSongRepresentationFactory) {
        super(uriInfo);
        this.playlistSongRepresentationFactory = playlistSongRepresentationFactory;
    }

    /**
     * Returns a new {@link Representation} of a zone playlist.
     *
     * @param zonePlaylistDto a zone playlist
     * @return a new {@link Representation} of the playlist
     */
    public Representation newRepresentation(final ZonePlaylistDto zonePlaylistDto) {
        final String getPlaylistResourceMethod = "getZonePlaylistResource";
        final String zoneName = zonePlaylistDto.getZoneName();
        final URI self = getBaseURIBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, getPlaylistResourceMethod)
                .build(zoneName);

        final Representation representation = newRepresentation(self.toString())
                .withNamespace("mr", getMRNamespace());

        final Collection<PlaylistSong> playlistSongs = zonePlaylistDto.getSongs();
        playlistSongs.forEach(song -> representation.withRepresentation("mr:playlist-song", playlistSongRepresentationFactory.newRepresentation(zoneName, song)));
        return representation;
    }

}
