package com.autelhome.multiroom.zone;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ZoneConfigurationTest {

    @Test
    public void getName() throws Exception {
        final String expected = "a zone";
        final ZoneConfiguration zoneConfiguration = new ZoneConfiguration(expected, 1234);

        final String actual = zoneConfiguration.getName();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getMPDPort() throws Exception {
        final int expected = 4564;
        final ZoneConfiguration zoneConfiguration = new ZoneConfiguration("kitchen", expected);

        final int actual = zoneConfiguration.getMPDPort();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void toZone() throws Exception {

        final ZoneConfiguration zoneConfiguration = new ZoneConfiguration("this is my room", 42);
        final Zone expected = new Zone("this is my room");

        final Zone actual = zoneConfiguration.toZone();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final ZoneConfiguration zoneConfiguration1 = new ZoneConfiguration("whatever", 6487);
        final ZoneConfiguration zoneConfiguration2 = new ZoneConfiguration("whatever", 6487);
        final ZoneConfiguration zoneConfiguration3 = new ZoneConfiguration(null, 6487);
        final ZoneConfiguration zoneConfiguration4 = new ZoneConfiguration(null, 6487);

        assertThat(zoneConfiguration1).isEqualTo(zoneConfiguration1);
        assertThat(zoneConfiguration1).isEqualTo(zoneConfiguration2);
        assertThat(zoneConfiguration3).isEqualTo(zoneConfiguration4);
    }

    @Test
    @SuppressFBWarnings("EC_UNRELATED_TYPES")
    public void shouldNotBeEqual() throws Exception {
        final ZoneConfiguration zoneConfiguration1 = new ZoneConfiguration("room1", 6487);
        final ZoneConfiguration zoneConfiguration2 = new ZoneConfiguration("room2", 6487);
        final ZoneConfiguration zoneConfiguration3 = new ZoneConfiguration("room1", 6488);
        final ZoneConfiguration zoneConfiguration4 = new ZoneConfiguration(null, 6487);

        assertThat(zoneConfiguration1).isNotEqualTo(zoneConfiguration2);
        assertThat(zoneConfiguration1).isNotEqualTo(zoneConfiguration3);
        assertThat(zoneConfiguration2).isNotEqualTo(zoneConfiguration3);
        assertThat(zoneConfiguration1.equals(null)).isFalse(); // NOPMD


        assertThat(zoneConfiguration1).isNotEqualTo(zoneConfiguration4);
        assertThat(zoneConfiguration4).isNotEqualTo(zoneConfiguration1);

        assertThat(zoneConfiguration1.equals(" ")).isFalse(); // NOPMD


    }

    @Test
    public void hashCodeShoulBeTheSame() throws Exception {
        final int hashCode1 = new ZoneConfiguration("room3", 6487).hashCode();
        final int hashCode2 = new ZoneConfiguration("room3", 6487).hashCode();
        final int hashCode3 = new ZoneConfiguration(null, 6487).hashCode();
        final int hashCode4 = new ZoneConfiguration(null, 6487).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
        assertThat(hashCode3).isEqualTo(hashCode4);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int hashCode1 = new ZoneConfiguration("room4", 6487).hashCode();
        final int hashCode2 = new ZoneConfiguration("room5", 6487).hashCode();
        final int hashCode3 = new ZoneConfiguration("room4", 6488).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
        assertThat(hashCode2).isNotEqualTo(hashCode3);
    }

    @Test
    public void toStringShouldContainNameAndPort() {
        final String zoneName = "zone1";
        final String actual = new ZoneConfiguration(zoneName, 1234).toString();

        assertThat(actual).contains(zoneName);
        assertThat(actual).contains("1234");
    }
}