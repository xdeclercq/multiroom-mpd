package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.player.Pause;
import com.autelhome.multiroom.player.Play;
import com.autelhome.multiroom.player.Stop;
import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneRepository;
import org.bff.javampd.events.PlayerBasicChangeEvent;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class MPDPlayerChangeListenerTest {

    private final EventBus eventBus = mock(EventBus.class);
    private final ZoneRepository zoneRepository = mock(ZoneRepository.class);
    private final UUID zoneId = UUID.randomUUID();
    private final MPDPlayerChangeListener testSubject = new MPDPlayerChangeListener(zoneId, eventBus, zoneRepository);

    @Test
    public void playerBasicChangePaused() throws Exception {
        final PlayerBasicChangeEvent event = mock(PlayerBasicChangeEvent.class);

        final int zoneVersion = 345;
        final Zone zone = mock(Zone.class);
        when(zoneRepository.getById(zoneId)).thenReturn(zone);
        when(zone.getVersion()).thenReturn(zoneVersion);
        when(event.getStatus()).thenReturn(PlayerBasicChangeEvent.Status.PLAYER_PAUSED);
        testSubject.playerBasicChange(event);

        verify(eventBus).send(new Pause(zoneId, zoneVersion));
    }

    @Test
    public void playerBasicChangeUnpaused() throws Exception {
        final PlayerBasicChangeEvent event = mock(PlayerBasicChangeEvent.class);

        final int zoneVersion = 345;
        final Zone zone = mock(Zone.class);
        when(zoneRepository.getById(zoneId)).thenReturn(zone);
        when(zone.getVersion()).thenReturn(zoneVersion);
        when(event.getStatus()).thenReturn(PlayerBasicChangeEvent.Status.PLAYER_UNPAUSED);
        testSubject.playerBasicChange(event);

        verify(eventBus).send(new Play(zoneId, zoneVersion));
    }

    @Test
    public void playerBasicChangeStarted() throws Exception {
        final PlayerBasicChangeEvent event = mock(PlayerBasicChangeEvent.class);

        final int zoneVersion = 345;
        final Zone zone = mock(Zone.class);
        when(zoneRepository.getById(zoneId)).thenReturn(zone);
        when(zone.getVersion()).thenReturn(zoneVersion);
        when(event.getStatus()).thenReturn(PlayerBasicChangeEvent.Status.PLAYER_STARTED);
        testSubject.playerBasicChange(event);

        verify(eventBus).send(new Play(zoneId, zoneVersion));
    }

    @Test
    public void playerBasicChangeStopped() throws Exception {
        final PlayerBasicChangeEvent event = mock(PlayerBasicChangeEvent.class);

        final int zoneVersion = 345;
        final Zone zone = mock(Zone.class);
        when(zoneRepository.getById(zoneId)).thenReturn(zone);
        when(zone.getVersion()).thenReturn(zoneVersion);
        when(event.getStatus()).thenReturn(PlayerBasicChangeEvent.Status.PLAYER_STOPPED);
        testSubject.playerBasicChange(event);

        verify(eventBus).send(new Stop(zoneId, zoneVersion));
    }

    @Test
    public void playerBasicChangeBitrateChange() throws Exception {
        final PlayerBasicChangeEvent event = mock(PlayerBasicChangeEvent.class);

        final int zoneVersion = 345;
        final Zone zone = mock(Zone.class);
        when(zoneRepository.getById(zoneId)).thenReturn(zone);
        when(zone.getVersion()).thenReturn(zoneVersion);
        when(event.getStatus()).thenReturn(PlayerBasicChangeEvent.Status.PLAYER_BITRATE_CHANGE);
        testSubject.playerBasicChange(event);

        verify(eventBus, never()).send(any());
    }
}