package com.autelhome.multiroom.util;

import com.autelhome.multiroom.player.PlayCommand;
import com.autelhome.multiroom.player.StopCommand;
import com.autelhome.multiroom.zone.Zone;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class HotAsyncEventBusTest {

    private final HotAsyncEventBus testSubject = new HotAsyncEventBus();

    @Test
    public void registerAndSend() throws Exception {

        final CommandHandler<PlayCommand> playCommandCommandHandler = mock(CommandHandler.class);
        testSubject.register(playCommandCommandHandler, PlayCommand.class);

        final CommandHandler<StopCommand> stopCommandCommandHandler = mock(CommandHandler.class);
        testSubject.register(stopCommandCommandHandler, StopCommand.class);
        testSubject.register(stopCommandCommandHandler, StopCommand.class);

        final PlayCommand playCommand = new PlayCommand(new Zone("my zone"));
        testSubject.send(playCommand);

        final StopCommand stopCommand = new StopCommand(new Zone("my other zone"));
        testSubject.send(stopCommand);

        verify(playCommandCommandHandler, times(1)).handle(playCommand);
        verify(stopCommandCommandHandler, times(2)).handle(stopCommand);
    }

    @Test
    public void sendWithoutRegistering() throws Exception {
        final PlayCommand playCommand = new PlayCommand(new Zone("my zone"));
        testSubject.send(playCommand);
    }


}