package com.autelhome.multiroom.app;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Application providing a RESTful API to manage a multi-instance MPD installation.
 *
 * @author xdeclercq
 */
public class MultiroomMPDApplication extends Application<MultiroomMPDConfiguration> {

    private GuiceBundle.Builder<MultiroomMPDConfiguration> guiceBuilder;

    /**
     * Entry point of the application.
     *
     * @param args the application arguments. See Dropwizard documentation.
     * @throws Exception if an exception occurs while running the application.
     */
    public static void main(final String... args) throws Exception {
        new MultiroomMPDApplication(GuiceBundle.<MultiroomMPDConfiguration>newBuilder()).run(args);
    }

    /**
     * Constructor.
     *
     * @param guiceBundleBuilder a {@link GuiceBundle.Builder<MultiroomMPDConfiguration>} instance
     */
    public MultiroomMPDApplication(final GuiceBundle.Builder<MultiroomMPDConfiguration> guiceBundleBuilder) {
        this.guiceBuilder = guiceBundleBuilder;
    }

    @Override
    public void initialize(final Bootstrap<MultiroomMPDConfiguration> bootstrap) {
        GuiceBundle<MultiroomMPDConfiguration> guiceBundle = guiceBuilder
                .addModule(new MultiroomMPDModule())
                .enableAutoConfig(
                        "com.autelhome.multiroom.util",
                        "com.autelhome.multiroom.app",
                        "com.autelhome.multiroom.zone")
                .setConfigClass(MultiroomMPDConfiguration.class)
                .build();

        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(final MultiroomMPDConfiguration configuration, final Environment environment) throws Exception {
        environment.getApplicationContext().setContextPath("/multiroom-mpd");
        environment.jersey().setUrlPattern("/api/*");
    }

}
