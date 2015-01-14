package com.autelhome.multiroom.app;

import com.autelhome.multiroom.zone.ZoneConfiguration;
import com.autelhome.multiroom.zone.ZonesConfiguration;
import com.google.common.io.Resources;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ApplicationConfigurationTest {

    @Test
    public void getZonesConfiguration() throws Exception {
        final ZonesConfiguration expected = new ZonesConfiguration();
        expected.add(new ZoneConfiguration("zone1", 1234));
        expected.add(new ZoneConfiguration("zone2", 4567));

        final ApplicationConfiguration configuration = new ApplicationConfiguration("mpdhost", expected);

        final ZonesConfiguration actual = configuration.getZonesConfiguration();

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void getMPDHost() throws Exception {
        final String expected = "mpdhost";

        final ApplicationConfiguration configuration = new ApplicationConfiguration(expected, new ZonesConfiguration());

        final String actual = configuration.getMPDHost();

        assertThat(actual, equalTo(expected));
    }


    @Test
    public void deserializeFromYAML() throws Exception {
        final ZonesConfiguration expected = new ZonesConfiguration();
        expected.add(new ZoneConfiguration("Kitchen", 6600));
        expected.add(new ZoneConfiguration("Bathroom", 6601));

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final ConfigurationFactory<ApplicationConfiguration> factory =
                new ConfigurationFactory<>(ApplicationConfiguration.class, validator, Jackson.newObjectMapper(), "dw");

        final ApplicationConfiguration actual = factory.build(new File(Resources.getResource(getClass(), "configuration.yml").toURI()));

        assertThat(actual.getZonesConfiguration(), equalTo(expected));
    }
}