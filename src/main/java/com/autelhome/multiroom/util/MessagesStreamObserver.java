package com.autelhome.multiroom.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observer;

/**
 * Observer for the messages stream.
 *
* @author xdeclercq
*/
class MessagesStreamObserver implements Observer<Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagesStreamObserver.class);

    private final MessageHandler handler;

    /**
     * Constructor.
     *
     * @param handler a {@link MessageHandler} instance
     */
    public MessagesStreamObserver(final MessageHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onCompleted() {
        // do nothing
    }

    @Override
    public void onError(final Throwable e) {
        LOGGER.error("Error on stream", e);
    }

    @Override
    public void onNext(final Message message) {
        try {
            handler.handle(message);
        } catch (Exception e) {
            LOGGER.error("Error on stream: {}", e.getMessage());
        }
    }
}
