package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.song.SongRepresentationFactory;
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

    private final SongRepresentationFactory songRepresentationFactory;
    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     */
    @Inject
    public ZonePlaylistRepresentationFactory(final UriInfo uriInfo, final SongRepresentationFactory songRepresentationFactory) {
        super(uriInfo);
        this.songRepresentationFactory = songRepresentationFactory;
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

        final Collection<Song> songs = zonePlaylistDto.getSongs();
        songs.forEach(song -> representation.withRepresentation("mr:song", songRepresentationFactory.newRepresentation(song)));
        return representation;


    }

}
