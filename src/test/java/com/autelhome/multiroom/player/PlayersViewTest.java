package com.autelhome.multiroom.player;

import com.autelhome.multiroom.playlist.ZonePlaylist;
import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.util.InstanceNotFoundException;
import com.autelhome.multiroom.socket.SocketBroadcaster;
import com.autelhome.multiroom.zone.ZoneCreated;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class PlayersViewTest {

    public static final String ZONE_NAME = "a zone";
    private final PlayerDatabase playerDatabase = mock(PlayerDatabase.class);
    private final SocketBroadcaster socketBroadcaster = mock(SocketBroadcaster.class);
    private final PlayersView testSubject = new PlayersView(playerDatabase, socketBroadcaster);

    @Test
    public void handleCreated() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final PlayerStatus playerStatus = PlayerStatus.STOPPED;
        testSubject.handleCreated(new ZoneCreated(zoneId, ZONE_NAME, 2323, playerStatus, new ZonePlaylist(Arrays.asList(new Song("a"), new Song("b")))));

        verify(playerDatabase).add(new PlayerDto(zoneId, ZONE_NAME, playerStatus));
    }

    @Test
    public void handlePlayed() throws Exception {
        final UUID zoneId = UUID.randomUUID();

        final Optional<PlayerDto> playerDto = Optional.of(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.PAUSED));
        when(playerDatabase.getByZoneId(zoneId)).thenReturn(playerDto);

        testSubject.handlePlayed(new Played(zoneId));

        verify(playerDatabase).update(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.PLAYING));
    }


    @Test(expected = InstanceNotFoundException.class)
    public void handlePlayedForUnknownZone() throws Exception {
        final UUID zoneId = UUID.randomUUID();

        when(playerDatabase.getByZoneId(zoneId)).thenReturn(Optional.empty());

        testSubject.handlePlayed(new Played(zoneId));
    }

    @Test
    public void handlePaused() throws Exception {
        final UUID zoneId = UUID.randomUUID();

        final Optional<PlayerDto> playerDto = Optional.of(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.PLAYING));
        when(playerDatabase.getByZoneId(zoneId)).thenReturn(playerDto);

        testSubject.handlePaused(new Paused(zoneId));

        verify(playerDatabase).update(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.PAUSED));
    }

    @Test
    public void handleStopped() throws Exception {
        final UUID zoneId = UUID.randomUUID();

        final Optional<PlayerDto> playerDto = Optional.of(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.PLAYING));
        when(playerDatabase.getByZoneId(zoneId)).thenReturn(playerDto);

        testSubject.handleStopped(new Stopped(zoneId));

        verify(playerDatabase).update(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.STOPPED));
    }

    @Test
    public void handlePlayerStatusUpdated() throws Exception {
        final UUID zoneId = UUID.randomUUID();

        final Optional<PlayerDto> playerDto = Optional.of(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.PLAYING));
        when(playerDatabase.getByZoneId(zoneId)).thenReturn(playerDto);

        testSubject.handlePlayerStatusUpdated(new PlayerStatusUpdated(zoneId, PlayerStatus.PAUSED));

        verify(playerDatabase).update(new PlayerDto(zoneId, ZONE_NAME, PlayerStatus.PAUSED));
    }
}