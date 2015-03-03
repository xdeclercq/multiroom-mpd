package com.autelhome.multiroom.util;

import com.autelhome.multiroom.player.Play;
import com.autelhome.multiroom.player.Stop;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class RxEventBusTest {

    private final RxEventBus testSubject = new RxEventBus();

    @Test
    public void registerAndSend() throws Exception {

        final MessageHandler<Play> playCommandCommandHandler = mock(MessageHandler.class);
        testSubject.register(playCommandCommandHandler, Play.class);

        final MessageHandler<Stop> stopCommandCommandHandler = mock(MessageHandler.class);
        testSubject.register(stopCommandCommandHandler, Stop.class);
        testSubject.register(stopCommandCommandHandler, Stop.class);

        final Play play = new Play(UUID.randomUUID(), 1);
        testSubject.send(play);

        final Stop stop = new Stop(UUID.randomUUID(), 1);
        testSubject.send(stop);

        verify(playCommandCommandHandler, times(1)).handle(play);
        verify(stopCommandCommandHandler, times(2)).handle(stop);
    }

    @Test
    public void sendWithoutRegistering() throws Exception {
        final Play play = new Play(UUID.randomUUID(), 3);
        testSubject.send(play);
    }


}