package com.autelhome.multiroom.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observer;

/**
 * Observer for the command stream.
 *
* @author xdeclercq
*/
class CommandStreamObserver implements Observer<Command> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandStreamObserver.class);

    private final CommandHandler handler;

    /**
     * Constructor.
     *
     * @param handler a {@link CommandHandler} instance
     */
    public CommandStreamObserver(final CommandHandler handler) {
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
    public void onNext(final Command command) {
        handler.handle(command);
    }
}
