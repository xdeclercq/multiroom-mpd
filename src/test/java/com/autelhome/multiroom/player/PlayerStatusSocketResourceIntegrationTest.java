package com.autelhome.multiroom.player;

import com.autelhome.multiroom.app.ApplicationConfiguration;
import com.autelhome.multiroom.app.MultiroomMPDApplication;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ws.DefaultWebSocketListener;
import com.ning.http.client.ws.WebSocket;
import com.ning.http.client.ws.WebSocketUpgradeHandler;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.testing.junit.ConfigOverride;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.fest.assertions.api.Assertions.assertThat;

public class PlayerStatusSocketResourceIntegrationTest {

    private static final String ZONES_URL_FORMAT = "http://localhost:%d/multiroom-mpd/api/zones/";

    @Rule
    public final DropwizardAppRule<ApplicationConfiguration> rule =
            new DropwizardAppRule<>(MultiroomMPDApplication.class, "src/test/resources/com/autelhome/multiroom/app/configuration.yml",
                    ConfigOverride.config("server.connector.port", "8000"));

    private final Client client = new Client();
    private String zonesUrl;


    @Before
    public void setUp() throws Exception {
        zonesUrl = String.format(ZONES_URL_FORMAT, rule.getLocalPort());

        client.resource(
                String.format(zonesUrl, rule.getLocalPort()))
                .post(ClientResponse.class, "{\"name\": \"Kitchen\", \"mpdInstancePort\": 6600}");

        client.resource(
                String.format(zonesUrl, rule.getLocalPort()))
                .post(ClientResponse.class, "{\"name\": \"Bathroom\", \"mpdInstancePort\": 6601}");
    }



    @Test
    public void testPlaying() throws Exception {
        final AsyncHttpClient asyncClient = new AsyncHttpClient();
        final int localPort = rule.getLocalPort();


        final MyWebSocketListener webSocketListener = new MyWebSocketListener();
        final WebSocket webSocket = asyncClient
                .prepareGet(String.format("ws://localhost:%d/multiroom-mpd/ws/zones/Kitchen/player/status", localPort))
                .execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(webSocketListener).build())
                .get(10, TimeUnit.MINUTES);

        Thread.sleep(100);

        webSocket.close();

        assertThat(webSocketListener.lastMessageReceived).contains("PLAYING");
        assertThat(webSocketListener.isClosed).isTrue();
    }


    @Test
    public void testPlayingWithUnknownZoneName() throws Exception {
        final AsyncHttpClient asyncClient = new AsyncHttpClient();
        final int localPort = rule.getLocalPort();

        final MyWebSocketListener webSocketListener = new MyWebSocketListener();
        final WebSocket webSocket = asyncClient
                .prepareGet(String.format("ws://localhost:%d/multiroom-mpd/ws/zones/unknown/player/status", localPort))
                .execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(webSocketListener).build())
                .get(10, TimeUnit.MINUTES);

        Thread.sleep(100);

        webSocket.close();

        assertThat(webSocketListener.lastMessageReceived).contains("Resource Not Found");
        assertThat(webSocketListener.isClosed).isTrue();
    }

    @Test
    public void testPlayingWithBlankZoneName() throws Exception {
        final AsyncHttpClient asyncClient = new AsyncHttpClient();
        final int localPort = rule.getLocalPort();

        final MyWebSocketListener webSocketListener = new MyWebSocketListener();
        final WebSocket webSocket = asyncClient
                .prepareGet(String.format("ws://localhost:%d/multiroom-mpd/ws/zones/%%20/player/status", localPort))
                .execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(webSocketListener).build())
                .get(10, TimeUnit.MINUTES);

        Thread.sleep(100);

        webSocket.close();

        assertThat(webSocketListener.lastMessageReceived).contains("Invalid Resource");
        assertThat(webSocketListener.isClosed).isTrue();
    }

    private class MyWebSocketListener extends DefaultWebSocketListener {

        private String lastMessageReceived;
        private boolean isClosed;

        @Override
        public void onMessage(final String message) {
            lastMessageReceived = message;
        }

        @Override
        public void onOpen(final WebSocket websocket) {
            client.resource(
                    String.format(zonesUrl + "Kitchen/player/play", rule.getLocalPort()))
                    .post(ClientResponse.class);

            client.resource(
                    String.format(zonesUrl + "Kitchen/player/stop", rule.getLocalPort()))
                    .post(ClientResponse.class);

            client.resource(
                    String.format(zonesUrl + "Kitchen/player/play", rule.getLocalPort()))
                    .post(ClientResponse.class);
        }

        @Override
        public void onClose(final WebSocket websocket) {
            super.onClose(websocket);
            this.isClosed = true;
        }
    }
}