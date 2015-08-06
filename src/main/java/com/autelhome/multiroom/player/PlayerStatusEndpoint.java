package com.autelhome.multiroom.player;

import com.autelhome.multiroom.errors.*;
import com.autelhome.multiroom.socket.BroadcastException;
import com.autelhome.multiroom.socket.EndpointRegistry;
import com.autelhome.multiroom.socket.GuiceEndpointConfigurator;
import com.autelhome.multiroom.socket.SenderEndpoint;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

/**
 * Websocket  to get the status of a zone player.
 *
 * @author xdeclercq
 */
@ServerEndpoint(value = "/ws/zones/{zoneName}/player/status", configurator = GuiceEndpointConfigurator.class)
public class PlayerStatusEndpoint implements SenderEndpoint<PlayerStatus> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerStatusEndpoint.class);
    public static final String WS_PLAYER_STATUS_RESOURCE_ID_FORMAT = "/zones/%s/player/status";

    private final EndpointRegistry endpointRegistry;
    private final PlayerService playerService;
    private Session session;
    private String zoneName;

    /**
     * Constructor.
     *
     * @param endpointRegistry the endpoint registry
     * @param playerService a {@link PlayerService} instance
     */
    @Inject
    public PlayerStatusEndpoint(final EndpointRegistry endpointRegistry, final PlayerService playerService) {
        this.endpointRegistry = endpointRegistry;
        this.playerService = playerService;
    }

    @OnOpen
    @SuppressWarnings("unused")
    public void onOpen(final Session session, @PathParam("zoneName") final String encodedZoneName) throws UnsupportedEncodingException {
        this.session = session;
        zoneName = URLDecoder.decode(encodedZoneName, "UTF-8");

        if (StringUtils.isBlank(zoneName)) {
            LOGGER.error("zone name should not be blank");
            throw new InvalidResourceException("zone name should not be blank");
        }
        final Optional<PlayerDto> playerDto = playerService.getPlayerByZoneName(zoneName);
        if (!playerDto.isPresent()) {
            LOGGER.error("zone {} was not found", zoneName);
            throw new ResourceNotFoundException("zone", zoneName);
        }

        LOGGER.info("Session {} is opened for zone named {}", session.getId(), zoneName);
        endpointRegistry.register(getKey(zoneName), this);
    }

    @OnClose
    @SuppressWarnings("unused")
    public void onClose(final CloseReason closeReason) {
        LOGGER.info("Closing session " + session.getId() + " for cause " + closeReason.getCloseCode());
        endpointRegistry.unregister(this);
    }

    @OnError
    @SuppressWarnings("unused")
    public void onError(final Throwable t) {
        final ErrorCode errorCode = t instanceof ToClientException ? ((ToClientException) t).getErrorCode() : ErrorCode.UNKNOWN_ERROR;
        sendMessage(new ErrorRepresentationFactory(session.getRequestURI()).newRepresentation(errorCode, t.getMessage()).toString(RepresentationFactory.HAL_JSON));
    }

    @Override
    public void sendEntity(final PlayerStatus entity) {
        final String message = new PlayerStatusRepresentationFactory(session.getRequestURI()).newRepresentation(entity, zoneName).toString(RepresentationFactory.HAL_JSON);
        sendMessage(message);
    }

    private String getKey(final String zoneName) {
        return String.format(WS_PLAYER_STATUS_RESOURCE_ID_FORMAT, zoneName);
    }

    private void sendMessage(final String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new BroadcastException(session, e);
        }
    }
}
