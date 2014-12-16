package com.autelhome.multiroom.app;

import com.autelhome.multiroom.zone.ZonesConfiguration;
import com.google.inject.Module;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jetty.MutableServletContextHandler;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class MultiroomMPDApplicationTest {

    private final Environment environment = mock(Environment.class);
    private final JerseyEnvironment jersey = mock(JerseyEnvironment.class);
    private final MutableServletContextHandler contextHandler = mock(MutableServletContextHandler.class);
    private final GuiceBundle.Builder<MultiroomMPDConfiguration> guiceBuilder = mock(GuiceBundle.Builder.class);
    private final Bootstrap<MultiroomMPDConfiguration> bootstrap = mock(Bootstrap.class);
    private final ZonesConfiguration zonesConfiguration = new ZonesConfiguration();
    private final MultiroomMPDConfiguration configuration = new MultiroomMPDConfiguration(zonesConfiguration);

    private final MultiroomMPDApplication testSubject = new MultiroomMPDApplication(guiceBuilder);


    @Test
    public void main() throws Exception {
        MultiroomMPDApplication.main("server", "src/test/resources/com/autelhome/multiroom/app/configuration.yml");
    }

    @Before
    public void setUp() {
        when(environment.getApplicationContext()).thenReturn(contextHandler);
        when(environment.jersey()).thenReturn(jersey);
    }

    @Test
    public void initialize() throws Exception {

        final GuiceBundle<MultiroomMPDConfiguration> guiceBundle = mock(GuiceBundle.class);
        when(guiceBuilder.build()).thenReturn(guiceBundle);
        when(guiceBuilder.addModule(any(Module.class))).thenReturn(guiceBuilder);
        when(guiceBuilder.enableAutoConfig(anyString(), anyString(), anyString())).thenReturn(guiceBuilder);
        when(guiceBuilder.setConfigClass(MultiroomMPDConfiguration.class)).thenReturn(guiceBuilder);

        testSubject.initialize(bootstrap);

        verify(bootstrap).addBundle(guiceBundle);
    }

    @Test
    public void run() throws Exception {
        testSubject.run(configuration, environment);

        verify(contextHandler).setContextPath("/multiroom-mpd");
        verify(jersey).setUrlPattern("/api/*");
    }
}