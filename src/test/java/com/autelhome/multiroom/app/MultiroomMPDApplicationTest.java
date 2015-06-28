package com.autelhome.multiroom.app;

import be.tomcools.dropwizard.websocket.WebsocketBundle;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jetty.MutableServletContextHandler;
import io.dropwizard.jetty.setup.ServletEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.Servlet;
import javax.servlet.ServletRegistration;

import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class MultiroomMPDApplicationTest {

    private final Environment environment = mock(Environment.class);
    private final JerseyEnvironment jersey = mock(JerseyEnvironment.class);
    private final MutableServletContextHandler contextHandler = mock(MutableServletContextHandler.class);
    private final GuiceBundle<ApplicationConfiguration> guiceBundle = mock(GuiceBundle.class);
    private final Bootstrap<ApplicationConfiguration> bootstrap = mock(Bootstrap.class);
    private final ApplicationConfiguration configuration = new ApplicationConfiguration("mpdhost");
    private final WebsocketBundle websocketBundle = mock(WebsocketBundle.class);

    private final MultiroomMPDApplication testSubject = new MultiroomMPDApplication(guiceBundle, websocketBundle);


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

        testSubject.initialize(bootstrap);

        verify(bootstrap).addBundle(guiceBundle);
    }

    @Test
    public void run() throws Exception {

        final ServletEnvironment servlet = mock(ServletEnvironment.class);
        when(environment.servlets()).thenReturn(servlet);

        final ServletRegistration.Dynamic atmosphereServletHolder = mock(ServletRegistration.Dynamic.class);
        when(servlet.addServlet(anyString(), any(Servlet.class))).thenReturn(atmosphereServletHolder);

        testSubject.run(configuration, environment);
    }
}