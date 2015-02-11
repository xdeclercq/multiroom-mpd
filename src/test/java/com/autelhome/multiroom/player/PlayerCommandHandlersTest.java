package com.autelhome.multiroom.player;

import com.autelhome.multiroom.mpd.MPDPool;
import com.autelhome.multiroom.zone.Zone;
import org.bff.javampd.Player;
import org.bff.javampd.exception.MPDPlayerException;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author xdeclercq
 */
public class PlayerCommandHandlersTest {

    private static final Zone ZONE = new Zone("zone for play");
    private final MPDPool mpdPool = mock(MPDPool.class);
    private final PlayerCommandHandlers testSubject = new PlayerCommandHandlers(mpdPool);

    @Test
    public void handlePlay() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        when(mpdPool.getMPDPlayer(ZONE)).thenReturn(mpdPlayer);
        testSubject.handlePlay(new PlayCommand(ZONE));

        verify(mpdPlayer, times(1)).play();
    }

    @Test
    public void handlePlayWithException() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        when(mpdPool.getMPDPlayer(ZONE)).thenReturn(mpdPlayer);

        doThrow(MPDPlayerException.class).when(mpdPlayer).play();

        testSubject.handlePlay(new PlayCommand(ZONE));

        verify(mpdPlayer, times(1)).play();
    }

    @Test
    public void handlePause() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        when(mpdPool.getMPDPlayer(ZONE)).thenReturn(mpdPlayer);
        when(mpdPlayer.getStatus()).thenReturn(Player.Status.STATUS_PLAYING);
        testSubject.handlePause(new PauseCommand(ZONE));

        verify(mpdPlayer, times(1)).pause();
    }

    @Test
    public void handlePauseWhenPaused() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        when(mpdPool.getMPDPlayer(ZONE)).thenReturn(mpdPlayer);
        when(mpdPlayer.getStatus()).thenReturn(Player.Status.STATUS_PAUSED);
        testSubject.handlePause(new PauseCommand(ZONE));

        verify(mpdPlayer, never()).pause();
    }

    @Test
    public void handlePauseWhenStopped() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        when(mpdPool.getMPDPlayer(ZONE)).thenReturn(mpdPlayer);
        when(mpdPlayer.getStatus()).thenReturn(Player.Status.STATUS_STOPPED);
        testSubject.handlePause(new PauseCommand(ZONE));

        verify(mpdPlayer, never()).pause();
    }



    @Test
    public void handlePauseWithException() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        when(mpdPool.getMPDPlayer(ZONE)).thenReturn(mpdPlayer);
        when(mpdPlayer.getStatus()).thenReturn(Player.Status.STATUS_PLAYING);
        doThrow(MPDPlayerException.class).when(mpdPlayer).pause();

        testSubject.handlePause(new PauseCommand(ZONE));

        verify(mpdPlayer, times(1)).pause();
    }

    @Test
    public void handleStop() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        when(mpdPool.getMPDPlayer(ZONE)).thenReturn(mpdPlayer);
        testSubject.handleStop(new StopCommand(ZONE));

        verify(mpdPlayer, times(1)).stop();
    }

    @Test
    public void handleSopWithException() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        when(mpdPool.getMPDPlayer(ZONE)).thenReturn(mpdPlayer);

        doThrow(MPDPlayerException.class).when(mpdPlayer).stop();

        testSubject.handleStop(new StopCommand(ZONE));

        verify(mpdPlayer, times(1)).stop();
    }
}