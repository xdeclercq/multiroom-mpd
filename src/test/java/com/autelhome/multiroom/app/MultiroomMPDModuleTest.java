package com.autelhome.multiroom.app;

import com.autelhome.multiroom.zone.ZoneConfiguration;
import com.autelhome.multiroom.zone.ZonesConfiguration;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class MultiroomMPDModuleTest {

    private final MultiroomMPDModule testSubject = new MultiroomMPDModule();

    @Test
    public void getZonesConfiguration() throws Exception {
        final ZonesConfiguration expected = new ZonesConfiguration();
        expected.add(new ZoneConfiguration("Kitchen", 1234));
        expected.add(new ZoneConfiguration("Bedroom", 5678));
        final MultiroomMPDConfiguration configuration = new MultiroomMPDConfiguration(expected);

        ZonesConfiguration actual = testSubject.getZonesConfiguration(configuration);

        assertThat(actual).isEqualTo(expected);
    }
}