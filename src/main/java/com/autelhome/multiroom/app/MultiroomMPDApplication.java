package com.autelhome.multiroom.app;

import be.tomcools.dropwizard.websocket.WebsocketBundle;
import com.autelhome.multiroom.player.PlayerStatusEndpoint;
import com.autelhome.multiroom.util.InjectorLookup;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Application providing a RESTful API to manage a multi-instance MPD installation.
 *
 * @author xdeclercq
 */
public class MultiroomMPDApplication extends Application<ApplicationConfiguration> {

    private final GuiceBundle<ApplicationConfiguration> guiceBundle;
    private final WebsocketBundle websocketBundle;


    /**
     * Entry point of the application.
     *
     * @param args the application arguments. See Dropwizard documentation.
     * @throws Exception if an exception occurs while running the application.
     */
    public static void main(final String... args) throws Exception {
        final GuiceBundle.Builder<ApplicationConfiguration> guiceBundleBuilder = getApplicationConfigurationBuilder();
        final GuiceBundle<ApplicationConfiguration> guiceBundle = getGuiceBundle(guiceBundleBuilder);
        final WebsocketBundle websocketBundle = new WebsocketBundle();
        new MultiroomMPDApplication(guiceBundle, websocketBundle).run(args);
    }

    private static GuiceBundle<ApplicationConfiguration> getGuiceBundle(final GuiceBundle.Builder<ApplicationConfiguration> guiceBundleBuilder) {
        return guiceBundleBuilder
                .addModule(new ApplicationModule())
                .enableAutoConfig(
                        "com.autelhome.multiroom.hal",
                        "com.autelhome.multiroom.app",
                        "com.autelhome.multiroom.zone",
                        "com.autelhome.multiroom.player",
                        "com.autelhome.multiroom.errors",
                        "com.autelhome.multiroom.util")
                .setConfigClass(ApplicationConfiguration.class)
                .build();
    }

    private static GuiceBundle.Builder<ApplicationConfiguration> getApplicationConfigurationBuilder() {
        return GuiceBundle.<ApplicationConfiguration>newBuilder();
    }

    /**
     * Constructor.
     */
    @SuppressWarnings("unused")
    public MultiroomMPDApplication() {
        final GuiceBundle.Builder<ApplicationConfiguration> guiceBundleBuilder = getApplicationConfigurationBuilder();
        this.guiceBundle = getGuiceBundle(guiceBundleBuilder);
        this.websocketBundle = new WebsocketBundle();
    }

    /**
     * Constructor.
     *
     * @param guiceBundle a {@link GuiceBundle} instance
     * @param websocketBundle a {@link WebsocketBundle} instance
     */
    public MultiroomMPDApplication(final GuiceBundle<ApplicationConfiguration> guiceBundle, final WebsocketBundle websocketBundle) {
        this.guiceBundle = guiceBundle;
        this.websocketBundle = websocketBundle;
    }

    @Override
    public void initialize(final Bootstrap<ApplicationConfiguration> bootstrap) {
        bootstrap.addBundle(guiceBundle);

        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new AssetsBundle("/docs", "/docs", "index.html", "docs"));
        bootstrap.addBundle(websocketBundle);

    }

    @Override
    public void run(final ApplicationConfiguration configuration, final Environment environment) throws Exception {
        websocketBundle.addEndpoint(PlayerStatusEndpoint.class);

        final Injector injector = guiceBundle.getInjector();
        InjectorLookup.setInjector(injector);

        environment.servlets().addServletListeners(new MyGuiceServletContextListener(injector));
    }

    private static final class MyGuiceServletContextListener extends GuiceServletContextListener {
        private final Injector injector;

        public MyGuiceServletContextListener(final Injector injector) {
            this.injector = injector;
        }

        @Override
        protected Injector getInjector() {
            return injector;
        }
    }
}
