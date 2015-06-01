package com.autelhome.multiroom.player;

import com.autelhome.multiroom.errors.ResourceNotFoundException;
import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.ZoneDto;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.Optional;

/**
 * REST resource to control a player of a zone.
 *
 * @xdeclercq
 */
@Produces({RepresentationFactory.HAL_JSON})
public class PlayerResource {

    private static final String RESOURCE_TYPE = "player";
    private final PlayerRepresentationFactory playerRepresentationFactory;
	private final ZoneDto zoneDto;
    private final EventBus eventBus;
    private final PlayerService playerService;

    /**
	 * Constructor.
	 *
	 * @param zoneDto the zone to which the player is related
     * @param playerService a {@link PlayerService} instance
	 * @param playerRepresentationFactory a {@link PlayerRepresentationFactory} instance
     * @param eventBus the event bus
	 */
	public PlayerResource(final ZoneDto zoneDto, final PlayerService playerService, final PlayerRepresentationFactory playerRepresentationFactory, final EventBus eventBus) {
        this.zoneDto = zoneDto;
        this.playerService = playerService;
        this.playerRepresentationFactory = playerRepresentationFactory;
        this.eventBus = eventBus;
	}

    /**
     * Sends a play command to the event bus and returns a representation of the player.
     *
     * @return HTTP 202 with a representation of the player
     */
    @POST
    @Path("play")
    public Response play() {
        eventBus.send(new Play(zoneDto.getId(), zoneDto.getVersion()));

        final Optional<PlayerDto> playerDto = getPlayerDto();

        return Response.status(202).entity(playerRepresentationFactory.newRepresentation(playerDto.get())).build();
    }

    /**
     * Sends a pause command to the event bus and returns a representation of the player.
     *
     * @return HTTP 202 with a representation of the player
     */
    @POST
    @Path("pause")
    public Response pause() {
        eventBus.send(new Pause(zoneDto.getId(), zoneDto.getVersion()));

        final Optional<PlayerDto> playerDto = getPlayerDto();

        return Response.status(202).entity(playerRepresentationFactory.newRepresentation(playerDto.get())).build();
    }

    /**
     * Sends a stop command to the event bus and returns a representation of the player.
     *
     * @return HTTP 202 with a representation of the player
     */
    @POST
    @Path("stop")
    public Response stop() {
        eventBus.send(new Stop(zoneDto.getId(), zoneDto.getVersion()));

        final Optional<PlayerDto> playerDto = getPlayerDto();

        return Response.status(202).entity(playerRepresentationFactory.newRepresentation(playerDto.get())).build();
    }

	/**
	 * Returns a representation of the player.
	 *
	 * @return a representation of the player
	 */
	@GET
	public Response getPlayer() {
        final Optional<PlayerDto> playerDto = getPlayerDto();

        return Response.ok(playerRepresentationFactory.newRepresentation(playerDto.get())).build();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final PlayerResource that = (PlayerResource) o;

		return Objects.equals(zoneDto, that.zoneDto);
    }

	@Override
	public int hashCode() {
		return Objects.hashCode(zoneDto);
	}

    private Optional<PlayerDto> getPlayerDto() {
        final Optional<PlayerDto> playerDto = playerService.getPlayerByZoneName(zoneDto.getName());
        if (!playerDto.isPresent()) {
            throw new ResourceNotFoundException(RESOURCE_TYPE, zoneDto.getName());
        }
        return playerDto;
    }
}
