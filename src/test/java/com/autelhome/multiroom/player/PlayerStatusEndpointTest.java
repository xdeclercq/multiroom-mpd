package com.autelhome.multiroom.player;

import com.autelhome.multiroom.errors.InvalidResourceException;
import com.autelhome.multiroom.errors.ResourceNotFoundException;
import com.autelhome.multiroom.socket.BroadcastException;
import com.autelhome.multiroom.socket.EndpointRegistry;
import org.junit.Test;

import javax.websocket.CloseReason;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class PlayerStatusEndpointTest {

    private static final String MY_ZONE = "myzone";
    private static final URI REQUEST_URI = URI.create("http://localhost:1234/multiroom-mpd/ws/zones/myzone/player/status");
    final EndpointRegistry endpointRegistry = mock(EndpointRegistry.class);
    final PlayerService playerService = mock(PlayerService.class);
    final PlayerStatusEndpoint testSubject = new PlayerStatusEndpoint(endpointRegistry, playerService);

    @Test
    public void onOpen() throws Exception {
        onOpen(MY_ZONE);

        verify(endpointRegistry).register(String.format(PlayerStatusEndpoint.WS_PLAYER_STATUS_RESOURCE_ID_FORMAT, MY_ZONE), testSubject);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void onOpenWithUnknownZoneName() throws Exception {
        final Session session = mock(Session.class);
        final String zoneName = MY_ZONE;
        when(playerService.getPlayerByZoneName(zoneName)).thenReturn(Optional.empty());

        testSubject.onOpen(session, zoneName);
    }

    @Test(expected = InvalidResourceException.class)
    public void onOpenWithBlankZoneName() throws Exception {
        final Session session = mock(Session.class);

        testSubject.onOpen(session, "%20");
    }

    @Test
    public void onClose() throws Exception {
        onOpen(MY_ZONE);
        testSubject.onClose(new CloseReason(CloseReason.CloseCodes.NO_EXTENSION, "reason"));

        verify(endpointRegistry).unregister(testSubject);
    }

    @Test
    public void onErrorWithUnknownError() throws Exception {
        final Session session = onOpen(MY_ZONE);

        final RemoteEndpoint.Basic remote = mock(RemoteEndpoint.Basic.class);
        when(session.getBasicRemote()).thenReturn(remote);
        when(session.getRequestURI()).thenReturn(REQUEST_URI);

        testSubject.onError(new RuntimeException("unknown exception"));

        verify(remote).sendText(anyString());
    }

    @Test
    public void onError() throws Exception {
        final Session session = onOpen(MY_ZONE);

        final RemoteEndpoint.Basic remote = mock(RemoteEndpoint.Basic.class);
        when(session.getBasicRemote()).thenReturn(remote);
        when(session.getRequestURI()).thenReturn(REQUEST_URI);

        testSubject.onError(new InvalidResourceException("invalid zone name"));

        verify(remote).sendText(anyString());
    }


    @Test
    public void sendEntity() throws Exception {
        final Session session = onOpen(MY_ZONE);

        final RemoteEndpoint.Basic remote = mock(RemoteEndpoint.Basic.class);
        when(session.getBasicRemote()).thenReturn(remote);
        when(session.getRequestURI()).thenReturn(REQUEST_URI);

        testSubject.sendEntity(PlayerStatus.PAUSED);

        verify(remote).sendText(anyString());
    }

    @Test(expected = BroadcastException.class)
    public void sendEntityWithError() throws Exception {
        final Session session = onOpen(MY_ZONE);

        final RemoteEndpoint.Basic remote = mock(RemoteEndpoint.Basic.class);
        when(session.getBasicRemote()).thenReturn(remote);
        when(session.getRequestURI()).thenReturn(REQUEST_URI);
        doThrow(IOException.class).when(remote).sendText(anyString());

        testSubject.sendEntity(PlayerStatus.PAUSED);
    }

    private Session onOpen(final String zoneName) throws UnsupportedEncodingException {
        final Session session = mock(Session.class);
        final PlayerDto playerDto = new PlayerDto(UUID.randomUUID(), zoneName, PlayerStatus.PAUSED);
        when(playerService.getPlayerByZoneName(zoneName)).thenReturn(Optional.of(playerDto));

        testSubject.onOpen(session, zoneName);

        return session;
    }
}