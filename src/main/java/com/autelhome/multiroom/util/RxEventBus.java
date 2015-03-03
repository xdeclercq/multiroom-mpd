package com.autelhome.multiroom.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * "Hot" event bus.
 *
 * This is a "hot" event bus as it starts sending commands event as soon as it receives them and so any message handler
 * who registers to this event bus may start observing the sequence somewhere in the middle.
 *
 * @author xdeclercq
 */
public class RxEventBus implements EventBus {

    private static final Logger LOGGER = LoggerFactory.getLogger(RxEventBus.class);

    private final Map<Class, List<Subject<Message, Message>>> messagesStreams = new HashMap<>();

    @Override
    public <M extends Message>  void register(final MessageHandler<M> handler, final Class<M> messageClass) {
        LOGGER.info("Registering a handler for message type {}", messageClass);
        final Subject<Message, Message> messagesStream = new SerializedSubject<>(PublishSubject.<Message>create());

        if (!messagesStreams.containsKey(messageClass)) {
            messagesStreams.put(messageClass, new ArrayList<>());
        }
        final List<Subject<Message, Message>> messagesStreamsForClass = messagesStreams.get(messageClass);

        messagesStreamsForClass.add(messagesStream);
        messagesStream.subscribe(new MessagesStreamObserver(handler));
    }

    @Override
    public void send(final Command command) {
        publishOrSend(command);
    }

    @Override
    public void publish(final Event event) {
        publishOrSend(event);
    }

    private void publishOrSend(final Message message) {
        final Class<? extends Message> messageClass = message.getClass();

        final List<Subject<Message, Message>> messagesStreamsForClass = messagesStreams.get(messageClass);

        if (messagesStreamsForClass == null) {
            return;
        }

        messagesStreamsForClass
                .forEach(messagesStream -> messagesStream.onNext(message));
    }
}
