package com.autelhome.multiroom.zone;

import org.junit.Before;
import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.fest.assertions.api.Assertions.assertThat;

public class ZoneServiceTest {

    private ZonesConfiguration zonesConfiguration;
    private ZoneService testSubject;

    @Before
    public void setUp() throws Exception {
        ZonesConfiguration zonesConfiguration = new ZonesConfiguration();
        zonesConfiguration.add(new ZoneConfiguration("Kitchen", 4564));
        zonesConfiguration.add(new ZoneConfiguration("Bedroom", 1456));
        testSubject = new ZoneService(zonesConfiguration);
    }

    @Test
    public void getAll() throws Exception {
        SortedSet<Zone> expected = new TreeSet<>();
        expected.add(new Zone("Kitchen"));
        expected.add(new Zone("Bedroom"));
        
        SortedSet<Zone> actual = testSubject.getAll();

        assertThat(actual).isEqualTo(expected);
    }

}