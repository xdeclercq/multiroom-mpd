package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.mpd.MPDGateway;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ZoneCommandHandlersTest {

    private final ZoneRepository zoneRepository = mock(ZoneRepository.class);
    private final MPDGateway mpdGateway = mock(MPDGateway.class);
    private final ZoneCommandHandlers testSubject = new ZoneCommandHandlers(zoneRepository, mpdGateway);

    @Test
    public void handleCreate() throws Exception {
        final int mpdInstancePortNumber = 1234;
        testSubject.handleCreate(new CreateZone(UUID.randomUUID(), "my zone", mpdInstancePortNumber));

        verify(mpdGateway).getPlayerStatus(mpdInstancePortNumber);
        verify(zoneRepository).save(any(Zone.class), anyInt());
    }
}