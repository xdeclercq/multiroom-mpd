package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.player.ChangePlayerStatus;
import com.autelhome.multiroom.player.PlayerStatus;
import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneRepository;
import org.bff.javampd.events.PlayerBasicChangeEvent;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MPDPlayerChangeListenerTest {

    private final EventBus eventBus = mock(EventBus.class);
    private final ZoneRepository zoneRepository = mock(ZoneRepository.class);
    private final UUID zoneId = UUID.randomUUID();
    private final MPDPlayerChangeListener testSubject = new MPDPlayerChangeListener(zoneId, eventBus, zoneRepository);

    @Test
    public void playerBasicChange() throws Exception {
        final PlayerBasicChangeEvent event = mock(PlayerBasicChangeEvent.class);

        final int zoneVersion = 345;
        final Zone zone = mock(Zone.class);
        when(zoneRepository.getById(zoneId)).thenReturn(zone);
        when(zone.getVersion()).thenReturn(zoneVersion);
        when(event.getStatus()).thenReturn(PlayerBasicChangeEvent.Status.PLAYER_PAUSED);
        testSubject.playerBasicChange(event);

        verify(eventBus).send(new ChangePlayerStatus(zoneId, PlayerStatus.PAUSED, zoneVersion));
    }
}