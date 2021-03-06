package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import java.net.URI;
import javax.ws.rs.core.UriInfo;

/**
 * {@link BaseRepresentationFactory} for a {@link Zone}.
 *
 * @author xavier
 */
public class ZoneRepresentationFactory extends BaseRepresentationFactory {

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     */
    @Inject
    public ZoneRepresentationFactory(final UriInfo uriInfo) {
        super(uriInfo);
    }

    /**
     * Returns a new {@link Representation} of a zone.
     *
     * @param zone a zone
     * @return a new {@link Representation} of the zone
     */
    public Representation newRepresentation(final ZoneDto zone) {
        final URI self = getBaseURIBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, "getByName")
                .build(zone.getName());

        final URI player = getBaseURIBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, "getPlayerResource")
                .build(zone.getName());

        final URI zonePlaylist = getBaseURIBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, "getZonePlaylistResource")
                .build(zone.getName());

        return newRepresentation(self)
                .withNamespace("mr", getMRNamespace())
                .withProperty("name", zone.getName())
                .withProperty("mpdInstancePort", zone.getMpdInstancePortNumber())
                .withLink("mr:player", player)
                .withLink("mr:zone-playlist", zonePlaylist);
    }

}
