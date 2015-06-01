package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.player.PlayerStatus;
import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.ZoneRepository;
import org.bff.javampd.MPD;
import org.bff.javampd.Player;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MPDGatewayTest {

    private static final int MPD_INSTANCE_PORT_NUMBER = 12;

    private final MPDProvider mpdProvider = mock(MPDProvider.class);
    private final EventBus eventBus = mock(EventBus.class);
    private final ZoneRepository zoneRepository = mock(ZoneRepository.class);
    private final MPDGateway testSubject = new MPDGateway(mpdProvider, eventBus, zoneRepository);
    private final MPD mpd = mock(MPD.class);
    private final Player mpdPlayer = mock(Player.class);


    @Before
    public void setUp() {
        when(mpdProvider.getMPD(MPD_INSTANCE_PORT_NUMBER)).thenReturn(mpd);
        when(mpd.getPlayer()).thenReturn(mpdPlayer);
    }

    @Test
    public void getPlayerStatus() throws Exception {
        when(mpdPlayer.getStatus()).thenReturn(Player.Status.STATUS_PLAYING);
        final PlayerStatus actual = testSubject.getPlayerStatus(MPD_INSTANCE_PORT_NUMBER);

        assertThat(actual).isEqualTo(PlayerStatus.PLAYING);
    }

    @Test
    public void play() throws Exception {
        testSubject.play(MPD_INSTANCE_PORT_NUMBER);
        verify(mpdPlayer).play();
    }

    @Test
    public void pause() throws Exception {
        testSubject.pause(MPD_INSTANCE_PORT_NUMBER);
        verify(mpdPlayer).pause();
    }

    @Test
    public void stop() throws Exception {
        testSubject.stop(MPD_INSTANCE_PORT_NUMBER);
        verify(mpdPlayer).stop();
    }

}