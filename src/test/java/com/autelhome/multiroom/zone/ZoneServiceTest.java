package com.autelhome.multiroom.zone;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.fest.assertions.api.Assertions.assertThat;

public class ZoneServiceTest {

    private static final String KITCHEN = "Kitchen";
    private final ZoneService testSubject;

    public ZoneServiceTest() {
        final ZonesConfiguration zonesConfiguration = new ZonesConfiguration();
        zonesConfiguration.add(new ZoneConfiguration(KITCHEN, 4564));
        zonesConfiguration.add(new ZoneConfiguration("Bedroom", 1456));
        testSubject = new ZoneService(zonesConfiguration);
    }

    @Test
    public void getAll() throws Exception {
        final SortedSet<Zone> expected = new TreeSet<>();
        expected.add(new Zone(KITCHEN));
        expected.add(new Zone("Bedroom"));

        final SortedSet<Zone> actual = testSubject.getAll();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getByName() throws Exception {
        final Zone expected = new Zone(KITCHEN);
        final Zone actual = testSubject.getByName(KITCHEN);

        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = NoSuchElementException.class)
    public void getByNameUnknownZone() throws Exception {
        testSubject.getByName("zone3");
    }

}