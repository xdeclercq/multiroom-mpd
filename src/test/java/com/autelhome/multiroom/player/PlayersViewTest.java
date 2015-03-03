package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.InstanceNotFoundException;
import com.autelhome.multiroom.util.SocketBroadcaster;
import com.autelhome.multiroom.zone.ZoneCreated;
import org.atmosphere.cpr.BroadcasterFactory;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class PlayersViewTest {

    public static final String ZONE_NAME = "a zone";
    private final PlayerDatabase playerDatabase = mock(PlayerDatabase.class);
    private final BroadcasterFactory broadcasterFactory = mock(BroadcasterFactory.class);
    private final PlayersView testSubject = new PlayersView(playerDatabase, broadcasterFactory);

    @Test
    public void handleCreated() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final PlayerStatus playerStatus = PlayerStatus.STOPPED;
        testSubject.handleCreated(new ZoneCreated(zoneId, ZONE_NAME, 2323, playerStatus));

        verify(playerDatabase).add(new PlayerDto(zoneId, ZONE_NAME, playerStatus));
    }

    @Test
    public void handlePlayed() throws Exception {
        final UUID zoneId = UUID.randomUUID();

        final Optional<PlayerDto> playerDto = Optional.of(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.PAUSED));
        when(playerDatabase.getByZoneId(zoneId)).thenReturn(playerDto);
        final SocketBroadcaster broadcaster = mock(SocketBroadcaster.class);
        when(broadcasterFactory.lookup(SocketBroadcaster.class, String.format(PlayerStatusSocketResource.WS_PLAYER_STATUS_RESOURCE_ID_FORMAT, ZONE_NAME))).thenReturn(broadcaster);

        testSubject.handlePlayed(new Played(zoneId));

        verify(playerDatabase).update(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.PLAYING));

        verify(broadcaster).broadcastEntity(PlayerStatus.PLAYING);
    }


    @Test(expected = InstanceNotFoundException.class)
    public void handlePlayedForUnkownZone() throws Exception {
        final UUID zoneId = UUID.randomUUID();

        when(playerDatabase.getByZoneId(zoneId)).thenReturn(Optional.empty());

        testSubject.handlePlayed(new Played(zoneId));
    }

    @Test
    public void handlePaused() throws Exception {
        final UUID zoneId = UUID.randomUUID();

        final Optional<PlayerDto> playerDto = Optional.of(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.PLAYING));
        when(playerDatabase.getByZoneId(zoneId)).thenReturn(playerDto);
        final SocketBroadcaster broadcaster = mock(SocketBroadcaster.class);
        when(broadcasterFactory.lookup(SocketBroadcaster.class, String.format(PlayerStatusSocketResource.WS_PLAYER_STATUS_RESOURCE_ID_FORMAT, ZONE_NAME))).thenReturn(broadcaster);

        testSubject.handlePaused(new Paused(zoneId));

        verify(playerDatabase).update(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.PAUSED));

        verify(broadcaster).broadcastEntity(PlayerStatus.PAUSED);

    }

    @Test
    public void handleStopped() throws Exception {
        final UUID zoneId = UUID.randomUUID();

        final Optional<PlayerDto> playerDto = Optional.of(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.PLAYING));
        when(playerDatabase.getByZoneId(zoneId)).thenReturn(playerDto);
        final SocketBroadcaster broadcaster = mock(SocketBroadcaster.class);
        when(broadcasterFactory.lookup(SocketBroadcaster.class, String.format(PlayerStatusSocketResource.WS_PLAYER_STATUS_RESOURCE_ID_FORMAT, ZONE_NAME))).thenReturn(broadcaster);

        testSubject.handleStopped(new Stopped(zoneId));

        verify(playerDatabase).update(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.STOPPED));

        verify(broadcaster).broadcastEntity(PlayerStatus.STOPPED);
    }

    @Test
    public void handlePlayerStatusUpdated() throws Exception {
        final UUID zoneId = UUID.randomUUID();

        final Optional<PlayerDto> playerDto = Optional.of(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.PLAYING));
        when(playerDatabase.getByZoneId(zoneId)).thenReturn(playerDto);
        final SocketBroadcaster broadcaster = mock(SocketBroadcaster.class);
        when(broadcasterFactory.lookup(SocketBroadcaster.class, String.format(PlayerStatusSocketResource.WS_PLAYER_STATUS_RESOURCE_ID_FORMAT, ZONE_NAME))).thenReturn(broadcaster);

        testSubject.handlePlayerStatusUpdated(new PlayerStatusUpdated(zoneId, PlayerStatus.PAUSED));

        verify(playerDatabase).update(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.PAUSED));

        verify(broadcaster).broadcastEntity(PlayerStatus.PAUSED);

    }
}