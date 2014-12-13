package com.autelhome.multiroom.zone;

import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ZonesConfigurationTest {

    @Test
    public void toZones() throws Exception {


        String zone1 = "zone1";
        String zone2 = "zone2";
        SortedSet<Zone> expected = new TreeSet<>();
        expected.add(new Zone(zone2));
        expected.add(new Zone(zone1));

        ZonesConfiguration zonesConfiguration = new ZonesConfiguration();
        zonesConfiguration.add(new ZoneConfiguration(zone1, 1234));
        zonesConfiguration.add(new ZoneConfiguration(zone2, 6547));

        SortedSet<Zone> actual = zonesConfiguration.toZones();

        assertThat(actual, equalTo(expected));
    }
}