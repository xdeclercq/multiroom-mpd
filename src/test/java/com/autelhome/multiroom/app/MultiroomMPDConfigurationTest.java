package com.autelhome.multiroom.app;

import com.autelhome.multiroom.zone.ZoneConfiguration;
import com.autelhome.multiroom.zone.ZonesConfiguration;
import org.junit.Test;

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
}