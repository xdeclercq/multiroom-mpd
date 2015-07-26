package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.app.ApplicationConfiguration;
import com.autelhome.multiroom.app.MultiroomMPDApplication;
import com.squarespace.jersey2.guice.BootstrapUtils;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;


public class ZonePlaylistResourceIntegrationTest {

    private static final String DOCS_URL_FORMAT = "http://localhost:%d/multiroom-mpd/docs/#/relations/{rel}";
    private static final String PLAYLIST_URL_FORMAT = "http://localhost:%d/multiroom-mpd/api/zones/Kitchen/playlist";
    private static final String UNKNOWN_PLAYLIST_URL_FORMAT = "http://localhost:%d/multiroom-mpd/api/zones/unknown/playlist";
    private static final String ERROR_INFO_URL_FORMAT = "http://localhost:%d/multiroom-mpd/docs/#/errors/resource-not-found";
    private static final String LINKS_SELF_HREF_PATH = "_links.self.href";
    private static final String LINKS_CURIES_NAME_PATH = "_links.curies.name";
    private static final String LINKS_CURIES_HREF_PATH = "_links.curies.href";
    private static final String LINKS_CURIES_TEMPLATED_PATH = "_links.curies.templated";

    @Rule
	public final DropwizardAppRule<ApplicationConfiguration> rule =
			new DropwizardAppRule<>(MultiroomMPDApplication.class, "src/test/resources/com/autelhome/multiroom/app/configuration.yml",
					ConfigOverride.config("server.connector.port", "8001"));
    private final Client client = new JerseyClientBuilder().build();

    @Before
    public void setUp() throws Exception {
        final String zonesUrl = String.format("http://localhost:%d/multiroom-mpd/api/zones/", rule.getLocalPort());

        client.target(
                String.format(zonesUrl, rule.getLocalPort()))
                .request()
                .post(Entity.json("{\"name\": \"Kitchen\", \"mpdInstancePort\": 1234}"));
    }

    @After
    public  void tearDown() throws Exception {
        BootstrapUtils.reset();
    }

    @Test
	public void get() throws Exception {
        final String selfUrl = String.format(PLAYLIST_URL_FORMAT, rule.getLocalPort());
        final String docsUrl = String.format(DOCS_URL_FORMAT, rule.getLocalPort());

        when().get(selfUrl)
			.then().assertThat()
            .statusCode(is(equalTo(200)))
			.and().body(LINKS_SELF_HREF_PATH, is(equalTo(selfUrl)))
			.and().body(LINKS_CURIES_NAME_PATH, is(equalTo("mr")))
            .and().body(LINKS_CURIES_HREF_PATH, is(equalTo(docsUrl)))
            .and().body(LINKS_CURIES_TEMPLATED_PATH, is(equalTo(true)));
    }

    @Test
    public void getWithUnkownZone() throws Exception {
        final String selfUrl = String.format(UNKNOWN_PLAYLIST_URL_FORMAT, rule.getLocalPort());
        final String docsUrl = String.format(DOCS_URL_FORMAT, rule.getLocalPort());
        final String errorInfoUrl = String.format(ERROR_INFO_URL_FORMAT, rule.getLocalPort());

        when().get(selfUrl)
                .then().assertThat()
                .statusCode(is(equalTo(404)))
                .and().body(LINKS_CURIES_NAME_PATH, is(equalTo("mr")))
                .and().body(LINKS_CURIES_HREF_PATH, is(equalTo(docsUrl)))
                .and().body(LINKS_CURIES_TEMPLATED_PATH, is(equalTo(true)))
                .and().body("_links[\"mr:error-info\"].href", is(equalTo(errorInfoUrl)))
                .and().body("errorCode", is(equalTo("Resource Not Found")))
                .and().body("message", is(equalTo("The 'unknown' zone was not found")));
    }

}