package com.autelhome.multiroom.util;

import com.autelhome.multiroom.player.PlayCommand;
import com.autelhome.multiroom.zone.Zone;
import org.bff.javampd.exception.MPDPlayerException;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class CommandStreamObserverTest {

    private final CommandHandler commandHandler = mock(CommandHandler.class);
    private final CommandStreamObserver testSubject = new CommandStreamObserver(commandHandler);

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
        final PlayCommand playCommand = new PlayCommand(new Zone("thezone"));
        testSubject.onNext(playCommand);

        verify(commandHandler, times(1)).handle(playCommand);
    }
}