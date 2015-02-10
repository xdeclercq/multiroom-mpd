package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.app.MultiroomMPDApplication;
import com.autelhome.multiroom.app.ApplicationConfiguration;
import io.dropwizard.testing.junit.ConfigOverride;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Rule;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.equalTo;


public class ZoneResourceIntegrationTest {

	@Rule
	public final DropwizardAppRule<ApplicationConfiguration> rule =
			new DropwizardAppRule<>(MultiroomMPDApplication.class, "src/test/resources/com/autelhome/multiroom/app/configuration.yml",
					ConfigOverride.config("server.connector.port", "8000"));

	@Test
	public void get() throws Exception {

        final String url = String.format("http://localhost:%d/multiroom-mpd/api/zones", rule.getLocalPort());
        final String docsUrl = String.format("http://localhost:%d/multiroom-mpd/docs/#/relations/{rel}", rule.getLocalPort());

		when().get(url)
			.then().assertThat()
			.body("_links.self.href", equalTo(url))
			.and().body("_links.curies.name", equalTo("mr"))
			.and().body("_links.curies.href", equalTo(docsUrl))
			.and().body("_links.curies.templated", equalTo(true))
			.and().body("_embedded.\"mr:zone\"[0].name", equalTo("Bathroom"))
			.and().body("_embedded.\"mr:zone\"[1].name", equalTo("Kitchen"));
	}
}