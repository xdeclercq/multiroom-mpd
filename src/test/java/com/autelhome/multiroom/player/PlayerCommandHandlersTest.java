package com.autelhome.multiroom.player;

import com.autelhome.multiroom.mpd.MPDPool;
import com.autelhome.multiroom.zone.Zone;
import org.bff.javampd.Player;
import org.bff.javampd.exception.MPDPlayerException;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class PlayerCommandHandlersTest {

    private final MPDPool mpdPool = mock(MPDPool.class);
    private final PlayerCommandHandlers testSubject = new PlayerCommandHandlers(mpdPool);

    @Test
    public void handlePlay() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        final Zone zone = new Zone("zone for play");
        when(mpdPool.getMPDPlayer(zone)).thenReturn(mpdPlayer);
        testSubject.handlePlay(new PlayCommand(zone));

        verify(mpdPlayer, times(1)).play();
    }

    @Test
    public void handleStop() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        final Zone zone = new Zone("zone for stop");
        when(mpdPool.getMPDPlayer(zone)).thenReturn(mpdPlayer);
        testSubject.handleStop(new StopCommand(zone));

        verify(mpdPlayer, times(1)).stop();
    }

    @Test
    public void handlePlayWithException() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        final Zone zone = new Zone("zone for play");
        when(mpdPool.getMPDPlayer(zone)).thenReturn(mpdPlayer);

        doThrow(MPDPlayerException.class).when(mpdPlayer).play();

        testSubject.handlePlay(new PlayCommand(zone));

        verify(mpdPlayer, times(1)).play();
    }

    @Test
    public void handleSopWithException() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        final Zone zone = new Zone("zone for stop");
        when(mpdPool.getMPDPlayer(zone)).thenReturn(mpdPlayer);

        doThrow(MPDPlayerException.class).when(mpdPlayer).stop();

        testSubject.handleStop(new StopCommand(zone));

        verify(mpdPlayer, times(1)).stop();
    }
}