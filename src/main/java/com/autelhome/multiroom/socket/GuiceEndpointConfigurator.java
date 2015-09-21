package com.autelhome.multiroom.socket;

import com.autelhome.multiroom.util.InjectorLookup;
import com.google.inject.Injector;
import javax.websocket.server.ServerEndpointConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configurator that uses Guice injector to get websocket endpoint instances.
 *
 * @author xdeclercq
 */
public class GuiceEndpointConfigurator extends ServerEndpointConfig.Configurator {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuiceEndpointConfigurator.class);

    @Override
    public <T> T getEndpointInstance(final Class<T> endpointClass) throws InstantiationException {
        final Injector injector = InjectorLookup.getInjector();
        LOGGER.info("injector {}", injector);
        final T endpoint = injector.getInstance(endpointClass);

        LOGGER.info(endpointClass.toString());
        return endpoint;
    }

}
