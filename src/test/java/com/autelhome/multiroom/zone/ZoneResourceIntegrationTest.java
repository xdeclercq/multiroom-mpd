package com.autelhome.multiroom.zone;

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

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.equalTo;


public class ZoneResourceIntegrationTest {

    public static final String ZONES_URL_FORMAT = "http://localhost:%d/multiroom-mpd/api/zones/";
    @Rule
	public final DropwizardAppRule<ApplicationConfiguration> rule =
			new DropwizardAppRule<>(MultiroomMPDApplication.class, "src/test/resources/com/autelhome/multiroom/app/configuration.yml",
					ConfigOverride.config("server.connector.port", "8000"));

    @Before
    public void setUp() throws Exception {
        final String zonesUrl = String.format(ZONES_URL_FORMAT, rule.getLocalPort());

        final Client client = new JerseyClientBuilder().build();

        client.target(
                String.format(zonesUrl, rule.getLocalPort()))
                .request()
                .post(Entity.json("{\"name\": \"Kitchen\", \"mpdInstancePort\": 5678}"));

        client.target(
                String.format(zonesUrl, rule.getLocalPort()))
                .request()
                .post(Entity.json("{\"name\": \"Bathroom\", \"mpdInstancePort\": 1234}"));
    }

    @After
    public  void tearDown() throws Exception {
        BootstrapUtils.reset();
    }

    @Test
	public void getAll() throws Exception {

        final String url = String.format(ZONES_URL_FORMAT, rule.getLocalPort());

        final String docsUrl = String.format("http://localhost:%d/multiroom-mpd/docs/#/relations/{rel}", rule.getLocalPort());

		when().get(url)
			.then().assertThat()
            .statusCode(200)
			.body("_links.self.href", equalTo(url))
			.and().body("_links.curies.name", equalTo("mr"))
			.and().body("_links.curies.href", equalTo(docsUrl))
			.and().body("_links.curies.templated", equalTo(true))
			.and().body("_embedded.\"mr:zone\"[0].name", equalTo("Bathroom"))
            .and().body("_embedded.\"mr:zone\"[0].mpdInstancePort", equalTo(1234))
            .and().body("_embedded.\"mr:zone\"[0]._links['mr:player'].href", equalTo(url + "Bathroom/player"))
			.and().body("_embedded.\"mr:zone\"[1].name", equalTo("Kitchen"))
            .and().body("_embedded.\"mr:zone\"[1].mpdInstancePort", equalTo(5678))
            .and().body("_embedded.\"mr:zone\"[0]._links['mr:player'].href", equalTo(url + "Bathroom/player"));
    }

    @Test
    public void getByName() throws Exception {
        final String url = String.format(ZONES_URL_FORMAT + "Kitchen", rule.getLocalPort());

        final String docsUrl = String.format("http://localhost:%d/multiroom-mpd/docs/#/relations/{rel}", rule.getLocalPort());

        when().get(url)
                .then().assertThat()
                .statusCode(200)
                .body("_links.self.href", equalTo(url))
                .and().body("_links.curies.name", equalTo("mr"))
                .and().body("_links.curies.href", equalTo(docsUrl))
                .and().body("_links.curies.templated", equalTo(true))
                .and().body("name", equalTo("Kitchen"))
                .and().body("mpdInstancePort", equalTo(5678))
                .and().body("_links['mr:player'].href", equalTo(url + "/player"));
    }

    @Test
    public void getByNameUnknownZone() throws Exception {
        final String url = String.format(ZONES_URL_FORMAT + "UnknownZone", rule.getLocalPort());

        final String docsUrl = String.format("http://localhost:%d/multiroom-mpd/docs/#/errors/resource-not-found", rule.getLocalPort());

        when().get(url)
                .then().assertThat()
                .statusCode(404)
                .body("_links['mr:error-info'].href", equalTo(docsUrl))
                .and().body("errorCode", equalTo("Resource Not Found"))
                .and().body("message", equalTo("The 'UnknownZone' zone was not found"));
    }

    @Test
    public void create() throws Exception {

        final String url = String.format(ZONES_URL_FORMAT, rule.getLocalPort());

        final String docsUrl = String.format("http://localhost:%d/multiroom-mpd/docs/#/relations/{rel}", rule.getLocalPort());

        given()
                .body("{\"name\": \"Library\", \"mpdInstancePort\": 1984}")
                .when().post(url)
                .then().assertThat()
                .statusCode(201)
                .header("Location", url + "Library")
                .body("_links.self.href", equalTo(url + "Library"))
                .and().body("_links.curies.name", equalTo("mr"))
                .and().body("_links.curies.href", equalTo(docsUrl))
                .and().body("_links.curies.templated", equalTo(true))
                .and().body("name", equalTo("Library"))
                .and().body("mpdInstancePort", equalTo(1984))
                .and().body("_links['mr:player'].href", equalTo(url + "Library/player"));
    }
}