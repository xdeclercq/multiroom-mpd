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

public class MultiroomMPDConfigurationTest {

    @Test
    public void getZonesConfiguration() throws Exception {
        ZonesConfiguration expected = new ZonesConfiguration();
        expected.add(new ZoneConfiguration("zone1", 1234));
        expected.add(new ZoneConfiguration("zone2", 4567));

        MultiroomMPDConfiguration configuration = new MultiroomMPDConfiguration(expected);

        ZonesConfiguration actual = configuration.getZonesConfiguration();

        assertThat(actual, equalTo(expected));
    }


    @Test
    public void deserializeFromYAML() throws Exception {
        ZonesConfiguration expected = new ZonesConfiguration();
        expected.add(new ZoneConfiguration("Kitchen", 6600));
        expected.add(new ZoneConfiguration("Bathroom", 6601));


        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final ConfigurationFactory<MultiroomMPDConfiguration> factory =
                new ConfigurationFactory<>(MultiroomMPDConfiguration.class, validator, Jackson.newObjectMapper(), "dw");


        MultiroomMPDConfiguration actual = factory.build(new File(Resources.getResource(MultiroomMPDConfigurationTest.class, "configuration.yml").toURI()));

        assertThat(actual.getZonesConfiguration(), equalTo(expected));
    }
}