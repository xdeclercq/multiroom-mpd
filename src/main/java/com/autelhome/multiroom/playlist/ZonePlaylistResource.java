package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.errors.ResourceNotFoundException;
import com.autelhome.multiroom.zone.ZoneDto;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.Optional;

/**
 * REST resource to fetch a playlist of a zone.
 *
 * @xdeclercq
 */
@Produces({RepresentationFactory.HAL_JSON})
public class ZonePlaylistResource {

    private static final String RESOURCE_TYPE = "playlist";
    private final ZonePlaylistRepresentationFactory zonePlaylistRepresentationFactory;
	private final ZoneDto zoneDto;
    private final ZonePlaylistService zonePlaylistService;

    /**
	 * Constructor.
	 *
	 * @param zoneDto the zone to which the player is related
     * @param zonePlaylistService a {@link ZonePlaylistService} instance
	 * @param zonePlaylistRepresentationFactory a {@link ZonePlaylistRepresentationFactory} instance
	 */
	public ZonePlaylistResource(final ZoneDto zoneDto, final ZonePlaylistService zonePlaylistService, final ZonePlaylistRepresentationFactory zonePlaylistRepresentationFactory) {
        this.zoneDto = zoneDto;
        this.zonePlaylistService = zonePlaylistService;
        this.zonePlaylistRepresentationFactory = zonePlaylistRepresentationFactory;
	}

	/**
	 * Returns a representation of the player.
	 *
	 * @return a representation of the player
	 */
	@GET
	public Response getPlaylist() {
        final Optional<ZonePlaylistDto> playlistDto = getPlaylistDto();

        return Response.ok(zonePlaylistRepresentationFactory.newRepresentation(playlistDto.get())).build();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final ZonePlaylistResource that = (ZonePlaylistResource) o;

		return Objects.equals(zoneDto, that.zoneDto);
    }

	@Override
	public int hashCode() {
		return Objects.hashCode(zoneDto);
	}

    private Optional<ZonePlaylistDto> getPlaylistDto() {
        final Optional<ZonePlaylistDto> playlistDto = zonePlaylistService.getPlaylistByZoneName(zoneDto.getName());
        if (!playlistDto.isPresent()) {
            throw new ResourceNotFoundException(RESOURCE_TYPE, zoneDto.getName());
        }
        return playlistDto;
    }
}
