package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.errors.ResourceNotFoundException;
import com.autelhome.multiroom.player.PlayerResource;
import com.autelhome.multiroom.player.PlayerResourceFactory;
import com.autelhome.multiroom.playlist.ZonePlaylistResource;
import com.autelhome.multiroom.playlist.ZonePlaylistResourceFactory;
import com.autelhome.multiroom.util.EventBus;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.net.URI;
import java.util.Optional;
import java.util.SortedSet;
import java.util.UUID;

/**
 * REST resource to fetch zones.
 *
 * @author xdeclercq
 */
@Path("/zones/")
@Produces({RepresentationFactory.HAL_JSON})
public class ZonesResource
{

    private static final String NAME = "name";
    private final ZoneService zoneService;
    private final ZonesRepresentationFactory zonesRepresentationFactory;
    private final PlayerResourceFactory playerResourceFactory;
    private final ZoneRepresentationFactory zoneRepresentationFactory;
    private final ZonePlaylistResourceFactory zonePlaylistResourceFactory;
    private final EventBus eventBus;

    /**
     * Constructor.
     *
     * @param zoneService an {@link ZoneService} instance
     * @param zonesRepresentationFactory a {@link ZonesRepresentationFactory} instance
     * @param zoneRepresentationFactory a {@link ZoneRepresentationFactory} instance
     * @param playerResourceFactory a {@link PlayerResourceFactory} instance
     * @param zonePlaylistResourceFactory a {@link ZonePlaylistResourceFactory} instance
     * @param eventBus the event bus
     */
    @Inject
    public ZonesResource(final ZoneService zoneService, final ZonesRepresentationFactory zonesRepresentationFactory, final ZoneRepresentationFactory zoneRepresentationFactory, final PlayerResourceFactory playerResourceFactory, final ZonePlaylistResourceFactory zonePlaylistResourceFactory, final EventBus eventBus) {
        this.zoneService = zoneService;
        this.zonesRepresentationFactory = zonesRepresentationFactory;
        this.zoneRepresentationFactory = zoneRepresentationFactory;
        this.playerResourceFactory = playerResourceFactory;
        this.zonePlaylistResourceFactory = zonePlaylistResourceFactory;

        this.eventBus = eventBus;
    }

    /**
     * Creates a new zone and returns its representation.
     *
     * @param zoneRepresentation a zone
     * @return the representation of the new zone
     */
    @POST
    public Response create(final String zoneRepresentation) {
        final ContentRepresentation contentRepresentation = zoneRepresentationFactory.readRepresentation(RepresentationFactory.HAL_JSON, new StringReader(zoneRepresentation));

        final String name = contentRepresentation.getProperties().get(NAME).toString();
        final int mpdInstancePortNumber = Integer.parseInt(contentRepresentation.getProperties().get("mpdInstancePort").toString());
        final UUID id = UUID.randomUUID();
        eventBus.send(new CreateZone(id, name, mpdInstancePortNumber));
        final ZoneDto zoneDto = new ZoneDto(id, name, mpdInstancePortNumber, -1);
        final Representation representation = zoneRepresentationFactory.newRepresentation(zoneDto);
        return Response.created(URI.create(representation.getLinkByRel("self").getHref())).entity(representation).build();
    }

    /**
     * Returns a representation of all zones.
     *
     * @return the representation of all zones
     */
    @GET
    public Response getAll() {
        final SortedSet<ZoneDto> zones = zoneService.getAll();
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
    public Response getByName(@PathParam(NAME) final String zoneName) {
        final Optional<ZoneDto> zoneDto = zoneService.getByName(zoneName);
        if (!zoneDto.isPresent()) {
            throw new ResourceNotFoundException("zone", zoneName);
        }
        final Representation representation = zoneRepresentationFactory.newRepresentation(zoneDto.get());
        return Response.ok(representation).build();
    }

    /**
     * Returns a PlayerResource for a zone.
     *
     * @param zoneName the name of a zone
     * @return a {@link PlayerResource} instance for the player linked the zone named {@code zoneName}
     */
    @Path("/{name}/player")
    public PlayerResource getPlayerResource(@PathParam(NAME) final String zoneName) {
        final Optional<ZoneDto> zoneDto = zoneService.getByName(zoneName);
        return playerResourceFactory.newInstance(zoneDto.get());
    }

    /**
     * Returns a ZonePlaylistResource for a zone.
     *
     * @param zoneName the name of a zone
     * @return a {@link ZonePlaylistResource} instance for the playlist linked the zone named {@code zoneName}
     */
    @Path("/{name}/playlist")
    public ZonePlaylistResource getZonePlaylistResource(@PathParam(NAME) final String zoneName) {
        final Optional<ZoneDto> zoneDto = zoneService.getByName(zoneName);
        return zonePlaylistResourceFactory.newInstance(zoneDto.get());
    }

}
