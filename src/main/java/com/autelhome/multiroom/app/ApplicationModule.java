package com.autelhome.multiroom.app;

import com.autelhome.multiroom.zone.ZonesConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Guice module.
 *
 * @author xdeclercq
 */
public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {
        // do nothing
    }

    /**
     * Returns the zones configuration.
     *
     * @param configuration the application configuration
     * @return the zones configuration
     */
    @Provides
    public ZonesConfiguration getZonesConfiguration(final ApplicationConfiguration configuration) {
        return configuration.getZonesConfiguration();
    }
}
