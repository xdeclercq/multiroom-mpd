package com.autelhome.multiroom.util;

import com.autelhome.multiroom.player.Play;
import org.bff.javampd.exception.MPDPlayerException;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class MessagesStreamObserverTest {

    private final MessageHandler commandHandler = mock(MessageHandler.class);
    private final MessagesStreamObserver testSubject = new MessagesStreamObserver(commandHandler);

    @Test
    public void onCompleted() throws Exception {
        testSubject.onCompleted();
    }

    @Test
    public void onError() throws Exception {
        testSubject.onError(new MPDPlayerException("ERROR"));
    }

    @Test
    public void onNext() throws Exception {
        final Play play = new Play(UUID.randomUUID(), 1);
        testSubject.onNext(play);

        verify(commandHandler, times(1)).handle(play);
    }

    @Test
    public void onNextShouldCatchExceptions() throws Exception {
        final Play play = new Play(UUID.randomUUID(), 1);

        doThrow(Exception.class).when(commandHandler).handle(play);
        testSubject.onNext(play);

        verify(commandHandler, times(1)).handle(play);
    }
}