package com.autelhome.multiroom.player;

import com.autelhome.multiroom.errors.ErrorCode;
import com.autelhome.multiroom.errors.ErrorRepresentationFactory;
import com.autelhome.multiroom.errors.InvalidResourceException;
import com.autelhome.multiroom.errors.ResourceNotFoundException;
import com.autelhome.multiroom.errors.ToClientException;
import com.autelhome.multiroom.socket.BroadcastException;
import com.autelhome.multiroom.socket.EndpointRegistry;
import com.autelhome.multiroom.socket.GuiceEndpointConfigurator;
import com.autelhome.multiroom.socket.SenderEndpoint;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    /**
     * Invoked when opening a session. Registers the endpoint.
     *
     * @param session a websocket {@link Session}
     * @param encodedZoneName an encoded zone name
     * @throws UnsupportedEncodingException when unable to decode the encoded zone name
     */
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

    /**
     * Invoked when a session is closed. Unregisters the remote endpoint.
     *
     * @param closeReason the close reason
     */
    @OnClose
    @SuppressWarnings("unused")
    public void onClose(final CloseReason closeReason) {
        LOGGER.info("Closing session " + session.getId() + " for cause " + closeReason.getCloseCode());
        endpointRegistry.unregister(this);
    }

    /**
     * Invoked when an error occurs. Sends an error representation to the registered remote endpoints.
     *
     * @param t an exception
     */
    @OnError
    @SuppressWarnings("unused")
    public void onError(final Throwable t) {
        final ErrorCode errorCode = t instanceof ToClientException ?
                ((ToClientException) t).getErrorCode() :
                ErrorCode.UNKNOWN_ERROR;
        sendMessage(new ErrorRepresentationFactory(session.getRequestURI())
                .newRepresentation(errorCode, t.getMessage())
                .toString(RepresentationFactory.HAL_JSON));
    }

    /**
     * Sends an entity to the registered remote endpoints.
     *
     * @param entity the entity to be sent
     */
    @Override
    public void sendEntity(final PlayerStatus entity) {
        final String message = new PlayerStatusRepresentationFactory(session.getRequestURI())
                .newRepresentation(entity, zoneName)
                .toString(RepresentationFactory.HAL_JSON);
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
