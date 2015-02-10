package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.app.ApplicationConfiguration;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneConfiguration;
import com.autelhome.multiroom.zone.ZonesConfiguration;
import org.bff.javampd.Player;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class MPDPoolTest {

    @Test
    public void getMPDPlayer() throws Exception {
        final ZonesConfiguration zonesConfiguration = new ZonesConfiguration();
        final String kitchen = "Kitchen";
        zonesConfiguration.add(new ZoneConfiguration(kitchen, 3454));
        zonesConfiguration.add(new ZoneConfiguration("Bathroom", 4578));
        final ApplicationConfiguration configuration = new ApplicationConfiguration("localhost", zonesConfiguration);
        final MPDPool testSubject = new MPDPool(configuration, zonesConfiguration);
        final Player actual = testSubject.getMPDPlayer(new Zone(kitchen));

        assertThat(actual).isNotNull();
    }

    @Test(expected = MPDPoolException.class)
    public void getMPDPlayerWithUnknownHostShouldThrowException() throws Exception {
        final ZonesConfiguration zonesConfiguration = new ZonesConfiguration();
        final String kitchen = "Kitchen";
        zonesConfiguration.add(new ZoneConfiguration(kitchen, 3454));
        zonesConfiguration.add(new ZoneConfiguration("Bathroom", 4578));
        final ApplicationConfiguration configuration = new ApplicationConfiguration("mpdhost", zonesConfiguration);
        final MPDPool testSubject = new MPDPool(configuration, zonesConfiguration);
        final Player actual = testSubject.getMPDPlayer(new Zone(kitchen));

        assertThat(actual).isNotNull();
    }
}