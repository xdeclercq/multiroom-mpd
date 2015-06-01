package com.autelhome.multiroom.util;

import com.autelhome.multiroom.player.PlayerStatus;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.atmosphere.cpr.BroadcasterFactory;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class SocketBroadcasterTest {

    private final BroadcasterFactory broadcasterFactory = mock(BroadcasterFactory.class);
    private final AtmosphereResourceFactory atmosphereResourceFactory = new AtmosphereResourceFactory(broadcasterFactory);
    private final SocketBroadcaster testSubject = new SocketBroadcaster(atmosphereResourceFactory);

    @Test
    public void broadcastEntity() throws Exception {
        testSubject.broadcastEntity(PlayerStatus.PAUSED);
    }
}