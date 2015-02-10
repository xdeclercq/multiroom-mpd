package com.autelhome.multiroom.player;

import com.autelhome.multiroom.app.ApplicationConfiguration;
import com.autelhome.multiroom.app.MultiroomMPDApplication;
import io.dropwizard.testing.junit.ConfigOverride;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Rule;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;


public class PlayerResourceIntegrationTest {

	@Rule
	public final DropwizardAppRule<ApplicationConfiguration> rule =
			new DropwizardAppRule<>(MultiroomMPDApplication.class, "src/test/resources/com/autelhome/multiroom/app/configuration.yml",
					ConfigOverride.config("server.connector.port", "8001"));

    @Test
	public void get() throws Exception {

        final String url = String.format("http://localhost:%d/multiroom-mpd/api/zones/Kitchen/player", rule.getLocalPort());
        final String docsUrl = String.format("http://localhost:%d/multiroom-mpd/docs/#/relations/{rel}", rule.getLocalPort());
        final String playUrl = String.format("http://localhost:%d/multiroom-mpd/api/zones/Kitchen/player/play", rule.getLocalPort());
        final String stopUrl = String.format("http://localhost:%d/multiroom-mpd/api/zones/Kitchen/player/stop", rule.getLocalPort());

		when().get(url)
			.then().assertThat()
            .statusCode(is(equalTo(200)))
			.and().body("_links.self.href", is(equalTo(url)))
			.and().body("_links.curies.name", is(equalTo("mr")))
            .and().body("_links.curies.href", is(equalTo(docsUrl)))
            .and().body("_links.curies.templated", is(equalTo(true)))
            .and().body("_links.\"mr:play\".href", is(equalTo(playUrl)))
            .and().body("_links.\"mr:stop\".href", is(equalTo(stopUrl)));
	}

    @Test
    public void play() throws Exception {

        final String url = String.format("http://localhost:%d/multiroom-mpd/api/zones/Kitchen/player/play", rule.getLocalPort());
        final String docsUrl = String.format("http://localhost:%d/multiroom-mpd/docs/#/relations/{rel}", rule.getLocalPort());
        final String playerUrl = String.format("http://localhost:%d/multiroom-mpd/api/zones/Kitchen/player", rule.getLocalPort());
        final String stopUrl = String.format("http://localhost:%d/multiroom-mpd/api/zones/Kitchen/player/stop", rule.getLocalPort());

        when().post(url)
                .then().assertThat()
                .statusCode(is(equalTo(202)))
                .and().body("_links.self.href", is(equalTo(playerUrl)))
                .and().body("_links.curies.name", is(equalTo("mr")))
                .and().body("_links.curies.href", is(equalTo(docsUrl)))
                .and().body("_links.curies.templated", is(equalTo(true)))
                .and().body("_links.\"mr:play\".href", is(equalTo(url)))
                .and().body("_links.\"mr:stop\".href", is(equalTo(stopUrl)));
    }

    @Test
    public void stop() throws Exception {

        final String url = String.format("http://localhost:%d/multiroom-mpd/api/zones/Bathroom/player/stop", rule.getLocalPort());
        final String docsUrl = String.format("http://localhost:%d/multiroom-mpd/docs/#/relations/{rel}", rule.getLocalPort());
        final String playerUrl = String.format("http://localhost:%d/multiroom-mpd/api/zones/Bathroom/player", rule.getLocalPort());
        final String playUrl = String.format("http://localhost:%d/multiroom-mpd/api/zones/Bathroom/player/play", rule.getLocalPort());

        when().post(url)
                .then().assertThat()
                .statusCode(is(equalTo(202)))
                .and().body("_links.self.href", is(equalTo(playerUrl)))
                .and().body("_links.curies.name", is(equalTo("mr")))
                .and().body("_links.curies.href", is(equalTo(docsUrl)))
                .and().body("_links.curies.templated", is(equalTo(true)))
                .and().body("_links.\"mr:play\".href", is(equalTo(playUrl)))
                .and().body("_links.\"mr:stop\".href", is(equalTo(url)));
    }
}