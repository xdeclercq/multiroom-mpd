package com.autelhome.multiroom.app;

import com.autelhome.multiroom.util.CorsFilter;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereServlet;
import org.atmosphere.guice.AtmosphereGuiceServlet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletRegistration;
import java.util.EnumSet;

/**
 * Application providing a RESTful API to manage a multi-instance MPD installation.
 *
 * @author xdeclercq
 */
public class MultiroomMPDApplication extends Application<ApplicationConfiguration> {

    public static final String CONTEXT_PATH = "/multiroom-mpd";
    private final GuiceBundle<ApplicationConfiguration> guiceBundle;


    /**
     * Entry point of the application.
     *
     * @param args the application arguments. See Dropwizard documentation.
     * @throws Exception if an exception occurs while running the application.
     */
    public static void main(final String... args) throws Exception {
        final GuiceBundle.Builder<ApplicationConfiguration> guiceBundleBuilder = getApplicationConfigurationBuilder();
        final GuiceBundle<ApplicationConfiguration> guiceBundle = getGuiceBundle(guiceBundleBuilder);
        new MultiroomMPDApplication(guiceBundle).run(args);
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
    }

    /**
     * Constructor.
     *
     * @param guiceBundle a {@link GuiceBundle} instance
     */
    public MultiroomMPDApplication(final GuiceBundle<ApplicationConfiguration> guiceBundle) {
        this.guiceBundle = guiceBundle;
    }

    @Override
    public void initialize(final Bootstrap<ApplicationConfiguration> bootstrap) {
        bootstrap.addBundle(guiceBundle);

        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new AssetsBundle("/docs", "/docs", "index.html", "docs"));

    }

    @Override
    public void run(final ApplicationConfiguration configuration, final Environment environment) throws Exception {
        environment.getApplicationContext().setContextPath(CONTEXT_PATH);
        environment.jersey().setUrlPattern("/api/*");
        initializeAtmosphere(environment);

        final Injector injector = guiceBundle.getInjector();

        environment.servlets().addServletListeners(new MyGuiceServletContextListener(injector));

    }

    private void initializeAtmosphere(final Environment environment) {
        final AtmosphereServlet atmosphereServlet = new AtmosphereGuiceServlet();
        atmosphereServlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "com.autelhome.multiroom.player");
        atmosphereServlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_CONTENT_TYPE, "application/hal+json");
        atmosphereServlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_SUPPORT, "true");

        final ServletRegistration.Dynamic atmosphereServletHolder = environment.servlets().addServlet("ws", atmosphereServlet);
        atmosphereServletHolder.addMapping("/ws/*");

        final FilterRegistration.Dynamic corsFilterHolder = environment.servlets().addFilter("CORS Filter", CorsFilter.class);
        corsFilterHolder.addMappingForServletNames(EnumSet.allOf(DispatcherType.class), true, "ws");
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
