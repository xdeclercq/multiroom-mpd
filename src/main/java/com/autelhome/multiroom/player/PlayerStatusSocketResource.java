package com.autelhome.multiroom.player;

import com.autelhome.multiroom.errors.ErrorCode;
import com.autelhome.multiroom.errors.ErrorRepresentationFactory;
import com.autelhome.multiroom.errors.ResourceNotFoundException;
import com.autelhome.multiroom.util.SocketBroadcaster;
import com.autelhome.multiroom.util.SocketMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import org.apache.commons.lang.StringUtils;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.PathParam;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.net.URI;
import java.util.Optional;

/**
 * Web Socket Resource to publish players status updates.
 *
 * @author xdeclercq
 */
@Path(PlayerStatusSocketResource.WS_ZONES_ZONE_NAME_PLAYER_STATUS_PATH)
@ManagedService(path = PlayerStatusSocketResource.WS_ZONES_ZONE_NAME_PLAYER_STATUS_PATH)
@Produces(RepresentationFactory.HAL_JSON)
public class PlayerStatusSocketResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerStatusSocketResource.class);
    public static final String WS_ZONES_ZONE_NAME_PLAYER_STATUS_PATH = "/ws/zones/{zoneName}/player/status";
    public static final String WS_PLAYER_STATUS_RESOURCE_ID_FORMAT = "zones/%s/player/status";

    @PathParam("zoneName")
    private String zoneName;

    private final BroadcasterFactory broadcasterFactory;
    private final AtmosphereResourceFactory atmosphereResourceFactory;
    private final PlayerService playerService;

    /**
     * Constructor.
     *
     * @param broadcasterFactory a {@link BroadcasterFactory} instance
     * @param atmosphereResourceFactory a {@link AtmosphereResourceFactory} instance
     * @param playerService a {@link PlayerService} instance
     */
    @Inject
    public PlayerStatusSocketResource(final BroadcasterFactory broadcasterFactory,
                                      final AtmosphereResourceFactory atmosphereResourceFactory, final PlayerService playerService) {
        this.broadcasterFactory = broadcasterFactory;
        this.atmosphereResourceFactory = atmosphereResourceFactory;
        this.playerService = playerService;
    }

    @Ready
    @SuppressWarnings("unused")
    public String onReady(final AtmosphereResource resource) {
        final URI requestURI = URI.create("ws" + resource.getRequest().getRequestURL().toString().substring(4));
        if (StringUtils.isBlank(zoneName)) {
            LOGGER.error("zone name should not be blank");
            return new ErrorRepresentationFactory(requestURI).newRepresentation(ErrorCode.INVALID_RESOURCE, "zone name should not be blank").toString(RepresentationFactory.HAL_JSON);
        }
        final Optional<PlayerDto> playerDto = playerService.getPlayerByZoneName(zoneName);
        if (!playerDto.isPresent()) {
            return new ErrorRepresentationFactory(requestURI).newRepresentation(ErrorCode.RESOURCE_NOT_FOUND, String.format(ResourceNotFoundException.MESSAGE_FORMAT, zoneName, "zone")).toString(RepresentationFactory.HAL_JSON);
        }

        resource.addEventListener(new AtmosphereResourceEventListenerAdapter() {
            @Override
            public void onSuspend(final AtmosphereResourceEvent event) {
                LOGGER.info("Socket resource {} connected on zone '{}'", resource.uuid(), zoneName);
                final Broadcaster broadcaster = broadcasterFactory.lookup(SocketBroadcaster.class, String.format(WS_PLAYER_STATUS_RESOURCE_ID_FORMAT, zoneName), true);
                atmosphereResourceFactory.registerUuidForFindCandidate(resource);
                broadcaster.addAtmosphereResource(resource);
            }

            @Override
            public void onDisconnect(final AtmosphereResourceEvent event) {
                LOGGER.info("Socket resource {} disconnected on zone '{}'", resource.uuid(), zoneName);
                detachResource();
            }

            private void detachResource() {
                atmosphereResourceFactory.unRegisterUuidForFindCandidate(resource);
                broadcasterFactory.removeAllAtmosphereResource(resource);
            }
        });

        return new PlayerStatusRepresentationFactory(requestURI).newRepresentation(playerDto.get().getStatus(), zoneName).toString(RepresentationFactory.HAL_JSON);
    }

    @Message
    @SuppressWarnings("unused")
    public String sendMessage(final SocketMessage<PlayerStatus> socketMessage) throws JsonProcessingException {
        final URI requestURI = socketMessage.getRequestURI();
        return new PlayerStatusRepresentationFactory(requestURI).newRepresentation(socketMessage.getEntity(), zoneName).toString(RepresentationFactory.HAL_JSON);
    }
}
