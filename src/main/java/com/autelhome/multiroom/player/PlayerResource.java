package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.EventBus;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Objects;

/**
 * REST resource to control a player of a zone.
 *
 * @xdeclercq
 */
@Produces({RepresentationFactory.HAL_JSON})
public class PlayerResource {

	private final PlayerRepresentationFactory playerRepresentationFactory;
	private final Player player;
    private final EventBus eventBus;

    /**
	 * Constructor.
	 *
	 * @param player a player
	 * @param playerRepresentationFactory a {@link PlayerRepresentationFactory} instance
     * @param eventBus the event bus
	 */
	public PlayerResource(final Player player, final PlayerRepresentationFactory playerRepresentationFactory, final EventBus eventBus) {
		this.playerRepresentationFactory = playerRepresentationFactory;
		this.player = player;
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
        eventBus.send(new PlayCommand(player.getZone()));

        return Response.status(202).entity(playerRepresentationFactory.newRepresentation(player)).build();
    }

    /**
     * Sends a pause command to the event bus and returns a representation of the player.
     *
     * @return HTTP 202 with a representation of the player
     */
    @POST
    @Path("pause")
    public Response pause() {
        eventBus.send(new PauseCommand(player.getZone()));

        return Response.status(202).entity(playerRepresentationFactory.newRepresentation(player)).build();
    }

    /**
     * Sends a stop command to the event bus and returns a representation of the player.
     *
     * @return HTTP 202 with a representation of the player
     */
    @POST
    @Path("stop")
    public Response stop() {
        eventBus.send(new StopCommand(player.getZone()));

        return Response.status(202).entity(playerRepresentationFactory.newRepresentation(player)).build();
    }

	/**
	 * Returns a representation of the player.
	 *
	 * @return a representation of the player
	 */
	@GET
	public Response getPlayer() {
		return Response.ok(playerRepresentationFactory.newRepresentation(player)).build();
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

		return Objects.equals(player, that.player);
    }

	@Override
	public int hashCode() {
		return Objects.hashCode(player);
	}
}
