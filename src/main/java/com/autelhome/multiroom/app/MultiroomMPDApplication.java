package com.autelhome.multiroom.app;

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

    private final GuiceBundle.Builder<ApplicationConfiguration> guiceBuilder;

    /**
     * Entry point of the application.
     *
     * @param args the application arguments. See Dropwizard documentation.
     * @throws Exception if an exception occurs while running the application.
     */
    public static void main(final String... args) throws Exception {
        new MultiroomMPDApplication(GuiceBundle.<ApplicationConfiguration>newBuilder()).run(args);
    }

    /**
     * Constructor.
     */
    public MultiroomMPDApplication() {
        this(GuiceBundle.<ApplicationConfiguration>newBuilder());
    }

    /**
     * Constructor.
     *
     * @param guiceBundleBuilder a {@link GuiceBundle.Builder< ApplicationConfiguration >} instance
     */
    public MultiroomMPDApplication(final GuiceBundle.Builder<ApplicationConfiguration> guiceBundleBuilder) {
        this.guiceBuilder = guiceBundleBuilder;
    }

    @Override
    public void initialize(final Bootstrap<ApplicationConfiguration> bootstrap) {
        final GuiceBundle<ApplicationConfiguration> guiceBundle = guiceBuilder
                .addModule(new ApplicationModule())
                .enableAutoConfig(
                        "com.autelhome.multiroom.hal",
                        "com.autelhome.multiroom.app",
                        "com.autelhome.multiroom.zone")
                .setConfigClass(ApplicationConfiguration.class)
                .build();

        bootstrap.addBundle(guiceBundle);

        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new AssetsBundle("/docs", "/docs", "index.html", "docs"));
    }

    @Override
    public void run(final ApplicationConfiguration configuration, final Environment environment) throws Exception {
        environment.getApplicationContext().setContextPath("/multiroom-mpd");
        environment.jersey().setUrlPattern("/api/*");
    }

}
