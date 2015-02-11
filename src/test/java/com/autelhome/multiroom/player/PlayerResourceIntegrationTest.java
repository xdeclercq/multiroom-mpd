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

    private static final String DOCS_URL_FORMAT = "http://localhost:%d/multiroom-mpd/docs/#/relations/{rel}";
    private static final String PLAYER_URL_FORMAT = "http://localhost:%d/multiroom-mpd/api/zones/Kitchen/player";
    private static final String PLAY_URL_FORMAT = "http://localhost:%d/multiroom-mpd/api/zones/Kitchen/player/play";
    private static final String PAUSE_URL_FORMAT = "http://localhost:%d/multiroom-mpd/api/zones/Kitchen/player/pause";
    private static final String STOP_URL_FORMAT = "http://localhost:%d/multiroom-mpd/api/zones/Kitchen/player/stop";
    private static final String LINKS_SELF_HREF_PATH = "_links.self.href";
    private static final String LINKS_CURIES_NAME_PATH = "_links.curies.name";
    private static final String LINKS_CURIES_HREF_PATH = "_links.curies.href";
    private static final String LINKS_CURIES_TEMPLATED_PATH = "_links.curies.templated";
    private static final String LINKS_MR_PLAY_HREF_PATH = "_links.\"mr:play\".href";
    private static final String LINKS_MR_PAUSE_HREF_PATH = "_links.\"mr:pause\".href";
    private static final String LINKS_MR_STOP_HREF_PATH = "_links.\"mr:stop\".href";
    @Rule
	public final DropwizardAppRule<ApplicationConfiguration> rule =
			new DropwizardAppRule<>(MultiroomMPDApplication.class, "src/test/resources/com/autelhome/multiroom/app/configuration.yml",
					ConfigOverride.config("server.connector.port", "8001"));

    @Test
	public void get() throws Exception {

        final String url = String.format(PLAYER_URL_FORMAT, rule.getLocalPort());
        final String docsUrl = String.format(DOCS_URL_FORMAT, rule.getLocalPort());
        final String playUrl = String.format(PLAY_URL_FORMAT, rule.getLocalPort());
        final String pauseUrl = String.format(PAUSE_URL_FORMAT, rule.getLocalPort());
        final String stopUrl = String.format(STOP_URL_FORMAT, rule.getLocalPort());

		when().get(url)
			.then().assertThat()
            .statusCode(is(equalTo(200)))
			.and().body(LINKS_SELF_HREF_PATH, is(equalTo(url)))
			.and().body(LINKS_CURIES_NAME_PATH, is(equalTo("mr")))
            .and().body(LINKS_CURIES_HREF_PATH, is(equalTo(docsUrl)))
            .and().body(LINKS_CURIES_TEMPLATED_PATH, is(equalTo(true)))
            .and().body(LINKS_MR_PLAY_HREF_PATH, is(equalTo(playUrl)))
            .and().body(LINKS_MR_PAUSE_HREF_PATH, is(equalTo(pauseUrl)))
            .and().body(LINKS_MR_STOP_HREF_PATH, is(equalTo(stopUrl)));
	}

    @Test
    public void play() throws Exception {

        final String url = String.format(PLAY_URL_FORMAT, rule.getLocalPort());
        final String docsUrl = String.format(DOCS_URL_FORMAT, rule.getLocalPort());
        final String playerUrl = String.format(PLAYER_URL_FORMAT, rule.getLocalPort());
        final String pauseUrl = String.format(PAUSE_URL_FORMAT, rule.getLocalPort());
        final String stopUrl = String.format(STOP_URL_FORMAT, rule.getLocalPort());

        when().post(url)
                .then().assertThat()
                .statusCode(is(equalTo(202)))
                .and().body(LINKS_SELF_HREF_PATH, is(equalTo(playerUrl)))
                .and().body(LINKS_CURIES_NAME_PATH, is(equalTo("mr")))
                .and().body(LINKS_CURIES_HREF_PATH, is(equalTo(docsUrl)))
                .and().body(LINKS_CURIES_TEMPLATED_PATH, is(equalTo(true)))
                .and().body(LINKS_MR_PLAY_HREF_PATH, is(equalTo(url)))
                .and().body(LINKS_MR_PAUSE_HREF_PATH, is(equalTo(pauseUrl)))
                .and().body(LINKS_MR_STOP_HREF_PATH, is(equalTo(stopUrl)));
    }

    @Test
    public void pause() throws Exception {

        final String url = String.format(PAUSE_URL_FORMAT, rule.getLocalPort());
        final String docsUrl = String.format(DOCS_URL_FORMAT, rule.getLocalPort());
        final String playerUrl = String.format(PLAYER_URL_FORMAT, rule.getLocalPort());
        final String playUrl = String.format(PLAY_URL_FORMAT, rule.getLocalPort());
        final String stopUrl = String.format(STOP_URL_FORMAT, rule.getLocalPort());

        when().post(url)
                .then().assertThat()
                .statusCode(is(equalTo(202)))
                .and().body(LINKS_SELF_HREF_PATH, is(equalTo(playerUrl)))
                .and().body(LINKS_CURIES_NAME_PATH, is(equalTo("mr")))
                .and().body(LINKS_CURIES_HREF_PATH, is(equalTo(docsUrl)))
                .and().body(LINKS_CURIES_TEMPLATED_PATH, is(equalTo(true)))
                .and().body(LINKS_MR_PLAY_HREF_PATH, is(equalTo(playUrl)))
                .and().body(LINKS_MR_PAUSE_HREF_PATH, is(equalTo(url)))
                .and().body(LINKS_MR_STOP_HREF_PATH, is(equalTo(stopUrl)));
    }

    @Test
    public void stop() throws Exception {

        final String url = String.format(STOP_URL_FORMAT, rule.getLocalPort());
        final String docsUrl = String.format(DOCS_URL_FORMAT, rule.getLocalPort());
        final String playerUrl = String.format(PLAYER_URL_FORMAT, rule.getLocalPort());
        final String playUrl = String.format(PLAY_URL_FORMAT, rule.getLocalPort());
        final String pauseUrl = String.format(PAUSE_URL_FORMAT, rule.getLocalPort());

        when().post(url)
                .then().assertThat()
                .statusCode(is(equalTo(202)))
                .and().body(LINKS_SELF_HREF_PATH, is(equalTo(playerUrl)))
                .and().body(LINKS_CURIES_NAME_PATH, is(equalTo("mr")))
                .and().body(LINKS_CURIES_HREF_PATH, is(equalTo(docsUrl)))
                .and().body(LINKS_CURIES_TEMPLATED_PATH, is(equalTo(true)))
                .and().body(LINKS_MR_PLAY_HREF_PATH, is(equalTo(playUrl)))
                .and().body(LINKS_MR_PAUSE_HREF_PATH, is(equalTo(pauseUrl)))
                .and().body(LINKS_MR_STOP_HREF_PATH, is(equalTo(url)));
    }
}