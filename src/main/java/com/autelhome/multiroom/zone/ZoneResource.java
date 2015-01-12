package com.autelhome.multiroom.zone;

import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
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


    /**
     * Constructor.
     *
     * @param zoneService an {@link ZoneService} instance
     * @param zonesRepresentationFactory a {@link ZonesRepresentationFactory} instance
     */
    @Inject
    public ZoneResource(final ZoneService zoneService, final ZonesRepresentationFactory zonesRepresentationFactory) {
        this.zoneService = zoneService;
        this.zonesRepresentationFactory = zonesRepresentationFactory;
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
}
