package com.autelhome.multiroom.player;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * REST resource to control a player of a zone.
 *
 * @xdeclercq
 */
@Produces({RepresentationFactory.HAL_JSON})
public class PlayerResource {

	private final PlayerRepresentationFactory playerRepresentationFactory;
	private final Player player;

	/**
	 * Constructor.
	 *
	 * @param player a player
	 * @param playerRepresentationFactory A {@link PlayerRepresentationFactory} instance
	 */
	public PlayerResource(final Player player, final PlayerRepresentationFactory playerRepresentationFactory) {
		this.playerRepresentationFactory = playerRepresentationFactory;
		this.player = player;
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

		if (player == null ? that.player != null : !player.equals(that.player)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return player == null ? 0 : player.hashCode();
	}
}
