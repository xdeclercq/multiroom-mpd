package com.autelhome.multiroom.zone;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotEquals;

public class ZoneConfigurationTest {

    @Test
    public void getName() throws Exception {
        String expected = "a zone";
        ZoneConfiguration zoneConfiguration = new ZoneConfiguration(expected, 1234);

        String actual = zoneConfiguration.getName();

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void getMPDPort() throws Exception {
        int expected = 4564;
        ZoneConfiguration zoneConfiguration = new ZoneConfiguration("kitchen", expected);

        int actual = zoneConfiguration.getMPDPort();

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void toZone() throws Exception {

        ZoneConfiguration zoneConfiguration = new ZoneConfiguration("this is my room", 42);
        Zone expected = new Zone("this is my room");

        Zone actual = zoneConfiguration.toZone();

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void shouldBeEqual() throws Exception {
        ZoneConfiguration zoneConfiguration1 = new ZoneConfiguration("whatever", 6487);
        ZoneConfiguration zoneConfiguration2 = new ZoneConfiguration("whatever", 6487);
        ZoneConfiguration zoneConfiguration3 = new ZoneConfiguration(null, 6487);
        ZoneConfiguration zoneConfiguration4 = new ZoneConfiguration(null, 6487);

        assertThat(zoneConfiguration1, equalTo(zoneConfiguration1));
        assertThat(zoneConfiguration1, equalTo(zoneConfiguration2));
        assertThat(zoneConfiguration3, equalTo(zoneConfiguration4));
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        ZoneConfiguration zoneConfiguration1 = new ZoneConfiguration("room1", 6487);
        ZoneConfiguration zoneConfiguration2 = new ZoneConfiguration("room2", 6487);
        ZoneConfiguration zoneConfiguration3 = new ZoneConfiguration("room1", 6488);
        ZoneConfiguration zoneConfiguration4 = new ZoneConfiguration(null, 6487);

        assertThat(zoneConfiguration1, not(equalTo(zoneConfiguration2)));
        assertThat(zoneConfiguration1, not(equalTo(zoneConfiguration3)));
        assertThat(zoneConfiguration2, not(equalTo(zoneConfiguration3)));
        assertThat(zoneConfiguration1, not(equalTo((ZoneConfiguration) null)));
        assertThat(zoneConfiguration4, not(equalTo(zoneConfiguration1)));
        assertNotEquals(zoneConfiguration1, " ");
    }

    @Test
    public void hashCodeShoulBeTheSame() throws Exception {
        int hashCode1 = new ZoneConfiguration("room3", 6487).hashCode();
        int hashCode2 = new ZoneConfiguration("room3", 6487).hashCode();
        int hashCode3 = new ZoneConfiguration(null, 6487).hashCode();
        int hashCode4 = new ZoneConfiguration(null, 6487).hashCode();

        assertThat(hashCode1, equalTo(hashCode2));
        assertThat(hashCode3, equalTo(hashCode4));
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        int hashCode1 = new ZoneConfiguration("room4", 6487).hashCode();
        int hashCode2 = new ZoneConfiguration("room5", 6487).hashCode();
        int hashCode3 = new ZoneConfiguration("room4", 6488).hashCode();

        assertThat(hashCode1, not(equalTo(hashCode2)));
        assertThat(hashCode1, not(equalTo(hashCode3)));
        assertThat(hashCode2, not(equalTo(hashCode3)));
    }
}