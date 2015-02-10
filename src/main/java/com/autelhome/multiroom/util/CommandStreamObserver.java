package com.autelhome.multiroom.util;

import rx.Observer;

/**
* @author xdeclercq
*/
class CommandStreamObserver implements Observer<Command> {

    private final CommandHandler handler;

    public CommandStreamObserver(final CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onCompleted() {
        // do nothing
    }

    @Override
    public void onError(final Throwable e) {
        HotAsyncEventBus.LOGGER.error("Error on stream", e);
    }

    @Override
    public void onNext(final Command command) {
        handler.handle(command);
    }
}
