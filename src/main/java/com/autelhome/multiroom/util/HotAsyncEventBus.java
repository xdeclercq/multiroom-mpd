package com.autelhome.multiroom.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observer;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * "Hot" asynchronous event bus.
 *
 * This is a "hot" event bus as it starts sending commands as soon as it receives them and so any command handler
 * who registers to this event bus may start observing the sequence somewhere in the middle.
 *
 * @author xdeclercq
 */
public class HotAsyncEventBus implements EventBus {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotAsyncEventBus.class);

    private final Map<Class, List<Subject<Command, Command>>> commandsStreams = new HashMap<>();

    private static class SimpleListener implements Observer<Command> {

        private final CommandHandler handler;

        public SimpleListener(final CommandHandler handler) {
            this.handler = handler;
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            LOGGER.error("Error on stream", e);
        }

        @Override
        public void onNext(Command command) {
            handler.handle(command);
        }
    }


    @Override
    public <C extends Command>  void register(final CommandHandler<C> handler, Class<C> clazz) {
        LOGGER.info("Registering a handler for command type {}", clazz);
        final Subject<Command, Command> commandStream = new SerializedSubject<>(PublishSubject.<Command>create());
        if (!commandsStreams.containsKey(clazz)) {
            commandsStreams.put(clazz, new ArrayList<>());
        }
        List<Subject<Command, Command>> commandStreamsForClass = commandsStreams.get(clazz);

        commandStreamsForClass.add(commandStream);
        commandStream.subscribe(new SimpleListener(handler));
    }

    @Override
    public void send(Command command) {
        final Class<? extends Command> commandClass = command.getClass();

        commandsStreams.get(commandClass)
                .forEach(commandStream -> commandStream.onNext(command));
    }
}
