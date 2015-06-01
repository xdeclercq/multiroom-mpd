package com.autelhome.multiroom.app;

import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.util.EventStore;
import com.autelhome.multiroom.util.InMemoryEventStore;
import com.autelhome.multiroom.util.RxEventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.guice.AtmosphereGuiceServlet;

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

    /**
     * Provides the {@link BroadcasterFactory} instance.
     *
     * @param atmosphereGuiceServlet the atmosphere guice servlet
     * @return the {@link BroadcasterFactory} instance
     */
    @Provides
    BroadcasterFactory provideBroadcasterFactory(final AtmosphereGuiceServlet atmosphereGuiceServlet) {
        return atmosphereGuiceServlet.framework().getBroadcasterFactory();
    }

    /**
     * Provides the {@link AtmosphereResourceFactory} instance.
     *
     * @param atmosphereGuiceServlet the atmosphere guice servlet
     * @return the {@link AtmosphereResourceFactory} instance
     */
    @Provides
    AtmosphereResourceFactory provideAtmosphereResourceFactory(final AtmosphereGuiceServlet atmosphereGuiceServlet) {
        return atmosphereGuiceServlet.framework().atmosphereFactory();
    }

}
