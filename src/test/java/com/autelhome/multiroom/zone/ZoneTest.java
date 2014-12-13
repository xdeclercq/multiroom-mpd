package com.autelhome.multiroom.zone;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.junit.Assert.assertNotEquals;

public class ZoneTest {

    @Test
    public void getName() throws Exception {
        String expected = "My Zone";
        Zone zone = new Zone(expected);

        String actual = zone.getName();

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void shouldBeEqual() throws Exception {
        Zone zone1 = new Zone("Kitchen");
        Zone zone2 = new Zone("Kitchen");
        Zone zone3 = new Zone(null);
        Zone zone4 = new Zone(null);

        assertThat(zone1, equalTo(zone1));
        assertThat(zone1, equalTo(zone2));
        assertThat(zone3, equalTo(zone4));
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        Zone zone1 = new Zone("Kitchen");
        Zone zone2 = new Zone("Bathroom");
        Zone zone3 = new Zone(null);

        assertThat(zone1, not(equalTo(zone2)));
        assertThat(zone1, not(equalTo((Zone) null)));
        assertThat(zone3, not(equalTo(zone1)));
        assertNotEquals(zone1, " ");
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        int hashCode1 = new Zone("Kitchen").hashCode();
        int hashCode2 = new Zone("Kitchen").hashCode();
        int hashCode3 = new Zone(null).hashCode();
        int hashCode4 = new Zone(null).hashCode();

        assertThat(hashCode1, equalTo(hashCode2));
        assertThat(hashCode3, equalTo(hashCode4));
    }

    @Test
    public void hashCodeShouldBeDifferent() throws Exception {
        int hashCode1 = new Zone("Kitchen").hashCode();
        int hashCode2 = new Zone("Bathroom").hashCode();

        assertThat(hashCode1, not(equalTo(hashCode2)));
    }

    @Test
    public void compareToShouldFollowLexicographicOrder() throws Exception {
        Zone zone1 = new Zone("Kitchen");
        Zone zone2 = new Zone("Bathroom");
        Zone zone3 = new Zone("Kitchen");
        Zone zone4 = new Zone("Office");

        assertThat(zone1, greaterThan(zone2));
        assertThat(zone1, comparesEqualTo(zone3));
        assertThat(zone1, lessThan(zone4));
        assertThat(zone2, lessThan(zone4));
    }
}
