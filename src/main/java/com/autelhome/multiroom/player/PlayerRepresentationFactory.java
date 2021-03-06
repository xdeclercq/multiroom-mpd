package com.autelhome.multiroom.player;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.autelhome.multiroom.zone.ZonesResource;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import java.net.URI;
import java.util.Optional;
import javax.ws.rs.core.UriInfo;

/**
 * {@link BaseRepresentationFactory} for a {@link PlayerDto}.
 *
 * @author xdeclercq
 */
public class PlayerRepresentationFactory extends BaseRepresentationFactory {

    private final PlayerStatusRepresentationFactory playerStatusRepresentationFactory;
    private final CurrentSongRepresentationFactory currentSongRepresentationFactory;

    /**
     * Constructor.
     *
     * @param uriInfo the {@link UriInfo} related to the request
     * @param playerStatusRepresentationFactory a {@link PlayerStatusRepresentationFactory} instance
     * @param currentSongRepresentationFactory a {@link CurrentSongRepresentationFactory} instance
     */
    @Inject
    public PlayerRepresentationFactory(final UriInfo uriInfo,
                                       final PlayerStatusRepresentationFactory playerStatusRepresentationFactory,
                                       final CurrentSongRepresentationFactory currentSongRepresentationFactory) {
        super(uriInfo);
        this.playerStatusRepresentationFactory = playerStatusRepresentationFactory;
        this.currentSongRepresentationFactory = currentSongRepresentationFactory;
    }

    /**
     * Returns a new {@link Representation} of a player.
     *
     * @param playerDto a player
     * @return a new {@link Representation} of the player
     */
    public Representation newRepresentation(final PlayerDto playerDto) {
        final String getPlayerResourceMethod = "getPlayerResource";
        final String zoneName = playerDto.getZoneName();
        final URI self = getBaseURIBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, getPlayerResourceMethod)
                .build(zoneName);

        final URI play = getBaseURIBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, getPlayerResourceMethod)
                .path(PlayerResource.class, "play")
                .build(zoneName);

        final URI playNext = getBaseURIBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, getPlayerResourceMethod)
                .path(PlayerResource.class, "playNext")
                .build(zoneName);

        final URI pause = getBaseURIBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, getPlayerResourceMethod)
                .path(PlayerResource.class, "pause")
                .build(zoneName);

        final URI stop = getBaseURIBuilder()
                .path(ZonesResource.class)
                .path(ZonesResource.class, getPlayerResourceMethod)
                .path(PlayerResource.class, "stop")
                .build(zoneName);

        final Representation representation = newRepresentation(self.toString())
                .withNamespace("mr", getMRNamespace())
                .withLink("mr:play", play)
                .withLink("mr:play-next", playNext)
                .withLink("mr:pause", pause)
                .withLink("mr:stop", stop)
                .withRepresentation("mr:status", playerStatusRepresentationFactory.newRepresentation(playerDto.getStatus(), zoneName));

        final Optional<CurrentSong> currentSong = playerDto.getCurrentSong();

        if (currentSong.isPresent()) {
            representation.withRepresentation("mr:current-song", currentSongRepresentationFactory.newRepresentation(currentSong.get()));
        }

        return representation;
    }

}
