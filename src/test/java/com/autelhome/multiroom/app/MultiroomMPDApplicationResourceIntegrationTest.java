package com.autelhome.multiroom.app;

import io.dropwizard.testing.junit.ConfigOverride;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Rule;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.equalTo;


public class MultiroomMPDApplicationResourceIntegrationTest {

	@Rule
	public final DropwizardAppRule<MultiroomMPDConfiguration> rule =
			new DropwizardAppRule<>(MultiroomMPDApplication.class, "src/test/resources/com/autelhome/multiroom/app/configuration.yml",
					ConfigOverride.config("server.connector.port", "8001"));

	@Test
	public void get() throws Exception {

		String url = String.format("http://localhost:%d/multiroom-mpd/api/", rule.getLocalPort());
		String docsUrl = String.format("http://localhost:%d/multiroom-mpd/api/docs/rels/{rel}", rule.getLocalPort());
		String zonesUrl = String.format("http://localhost:%d/multiroom-mpd/api/zones", rule.getLocalPort());

		when().get(url)
			.then().assertThat()
			.body("_links.self.href", equalTo(url))
			.and().body("_links.curies.name", equalTo("mr"))
			.and().body("_links.curies.href", equalTo(docsUrl))
			.and().body("_links.curies.templated", equalTo(true))
			.and().body("_links.\"mr:zones\".href", equalTo(zonesUrl));
	}
}