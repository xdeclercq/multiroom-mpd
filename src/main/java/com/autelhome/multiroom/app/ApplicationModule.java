package com.autelhome.multiroom.app;

import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.util.EventStore;
import com.autelhome.multiroom.util.InMemoryEventStore;
import com.autelhome.multiroom.util.RxEventBus;
import com.google.inject.AbstractModule;

/**
 * Guice module.
 *
 * @author xdeclercq
 */
public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {
        final RxEventBus eventBus = new RxEventBus();
        bind(EventBus.class).toInstance(eventBus);
        bind(EventStore.class).toInstance(new InMemoryEventStore(eventBus));
    }
}
