package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.player.PlayerResource;
import com.autelhome.multiroom.player.PlayerResourceFactory;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.SortedSet;

/**
 * REST resource to fetch zones.
 *
 * @author xdeclercq
 */
@Path("/zones")
@Produces({RepresentationFactory.HAL_JSON})
public class ZoneResource
{

    private final ZoneService zoneService;
    private final ZonesRepresentationFactory zonesRepresentationFactory;
    private final PlayerResourceFactory playerResourceFactory;
    private final ZoneRepresentationFactory zoneRepresentationFactory;

    /**
     * Constructor.
     *
     * @param zoneService an {@link ZoneService} instance
     * @param zonesRepresentationFactory a {@link ZonesRepresentationFactory} instance
     * @param zoneRepresentationFactory a {@link ZoneRepresentationFactory} instance
     * @param playerResourceFactory a {@link PlayerResourceFactory} instance
     */
    @Inject
    public ZoneResource(final ZoneService zoneService, final ZonesRepresentationFactory zonesRepresentationFactory, final ZoneRepresentationFactory zoneRepresentationFactory, final PlayerResourceFactory playerResourceFactory) {
        this.zoneService = zoneService;
        this.zonesRepresentationFactory = zonesRepresentationFactory;
        this.zoneRepresentationFactory = zoneRepresentationFactory;
        this.playerResourceFactory = playerResourceFactory;
    }

    /**
     * Returns a representation of all zones.
     *
     * @return the representation of all zones
     */
    @GET
    public Response get() {
        final SortedSet<Zone> zones = zoneService.getAll();
        final Representation representation = zonesRepresentationFactory.newRepresentation(zones);
        return Response.ok(representation).build();
    }

    /**
     * Returns a representation of a zone retrieved by its name.
     *
     * @param zoneName the name of a zone
     * @return the representation the zone named {@code zoneName}
     */
    @Path("/{name}")
    @GET
    public Response getByName(@PathParam("name") final String zoneName) {
        final Zone zone = zoneService.getByName(zoneName);
        final Representation representation = zoneRepresentationFactory.newRepresentation(zone);
        return Response.ok(representation).build();
    }

    /**
     * Returns a PlayerResource for a zone.
     *
     * @param zoneName the name of a zone
     * @return a {@link PlayerResource} instance for the player linked the zone named {@code zoneName}
     */
    @Path("/{name}/player")
    public PlayerResource getPlayerResource(@PathParam("name") final String zoneName) {
        return playerResourceFactory.newInstance(zoneService.getByName(zoneName));
    }
}
