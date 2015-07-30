package com.autelhome.multiroom.app;

import com.google.common.io.Resources;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationConfigurationTest {


    @Test
    public void getMPDHost() throws Exception {
        final String expected = "mpdhost";

        final ApplicationConfiguration configuration = new ApplicationConfiguration(expected);

        final String actual = configuration.getMPDHost();

        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void deserializeFromYAML() throws Exception {

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final ConfigurationFactory<ApplicationConfiguration> factory =
                new ConfigurationFactory<>(ApplicationConfiguration.class, validator, Jackson.newObjectMapper(), "dw");

        final ApplicationConfiguration actual = factory.build(new File(Resources.getResource(getClass(), "configuration.yml").toURI()));



        assertThat(actual.getMPDHost()).isEqualTo("localhost");
    }
}