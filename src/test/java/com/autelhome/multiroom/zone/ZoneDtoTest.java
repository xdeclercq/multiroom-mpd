package com.autelhome.multiroom.zone;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ZoneDtoTest {

    private static final String BEDROOM = "Bedroom";
    private static final String KITCHEN = "Kitchen";
    private static final String BATHROOM = "Bathroom";

    @Test
    public void getName() throws Exception {
        final String expected = "My Zone";
        final ZoneDto zone = new ZoneDto(UUID.randomUUID(), expected, 789, 1);

        final String actual = zone.getName();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final UUID id = UUID.randomUUID();
        final ZoneDto zone1 = new ZoneDto(id, BEDROOM, 789, 1);
        final ZoneDto zone2 = new ZoneDto(id, BEDROOM, 789, 1);
        final ZoneDto zone3 = new ZoneDto(id, null, 789, 1);
        final ZoneDto zone4 = new ZoneDto(id, null, 789, 1);
        final ZoneDto zone5 = new ZoneDto(id, BEDROOM, 789, 1);
        final ZoneDto zone6 = new ZoneDto(id, BEDROOM, 789, 1);

        assertThat(zone1).isEqualTo(zone1);
        assertThat(zone1).isEqualTo(zone2);
        assertThat(zone3).isEqualTo(zone4);
        assertThat(zone5).isEqualTo(zone6);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final UUID id1 = UUID.randomUUID();
        final ZoneDto zone1 = new ZoneDto(id1, KITCHEN, 789, 1);
        final ZoneDto zone2 = new ZoneDto(id1, BATHROOM, 789, 1);
        final ZoneDto zone3 = new ZoneDto(id1, null, 789, 1);
        final UUID id2 = UUID.randomUUID();
        final ZoneDto zone4 = new ZoneDto(id2, KITCHEN, 789, 1);
        final ZoneDto zone5 = new ZoneDto(id2, KITCHEN, 123, 1);
        final ZoneDto zone6 = new ZoneDto(null, KITCHEN, 123, 1);
        final ZoneDto zone7 = new ZoneDto(id2, KITCHEN, 789, 2);

        assertThat(zone1).isNotEqualTo(" ");
        assertThat(zone1).isNotEqualTo(null);
        assertThat(zone1).isNotEqualTo(zone2);
        assertThat(zone3).isNotEqualTo(zone1);
        assertThat(zone4).isNotEqualTo(zone1);
        assertThat(zone5).isNotEqualTo(zone4);
        assertThat(zone6).isNotEqualTo(zone5);
        assertThat(zone4).isNotEqualTo(zone7);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final UUID id = UUID.randomUUID();
        final int hashCode1 = new ZoneDto(id, "Hall", 789, 1).hashCode();
        final int hashCode2 = new ZoneDto(id, "Hall", 789, 1).hashCode();
        final int hashCode3 = new ZoneDto(id, null, 789, 1).hashCode();
        final int hashCode4 = new ZoneDto(id, null, 789, 1).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
        assertThat(hashCode3).isEqualTo(hashCode4);
    }

    @Test
    public void hashCodeShouldBeDifferent() throws Exception {
        final UUID id = UUID.randomUUID();
        final int hashCode1 = new ZoneDto(id, "Music room", 789, 1).hashCode();
        final int hashCode2 = new ZoneDto(id, BATHROOM, 789, 1).hashCode();
        final int hashCode3 = new ZoneDto(id, BATHROOM, 234, 1).hashCode();
        final int hashCode4 = new ZoneDto(id, BATHROOM, 234, 23).hashCode();
        final int hashCode5 = new ZoneDto(UUID.randomUUID(), BATHROOM, 234, 23).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode2).isNotEqualTo(hashCode3);
        assertThat(hashCode3).isNotEqualTo(hashCode4);
        assertThat(hashCode4).isNotEqualTo(hashCode5);
    }

    @Test
    public void compareToShouldFollowLexicographicOrder() throws Exception {
        final UUID id1 = UUID.randomUUID();
        final ZoneDto zone1 = new ZoneDto(id1, "Garden", 789, 1);
        final ZoneDto zone2 = new ZoneDto(id1, BATHROOM, 789, 1);
        final ZoneDto zone3 = new ZoneDto(id1, "Garden", 789, 1);
        final ZoneDto zone4 = new ZoneDto(id1, "Office", 789, 1);
        final UUID id2 = UUID.randomUUID();
        final ZoneDto zone5 = new ZoneDto(id2, "Office", 789, 1);

        assertThat(zone1).isGreaterThan(zone2);
        assertThat(zone1).usingDefaultComparator().isEqualTo(zone3);
        assertThat(zone1).isLessThan(zone4);
        assertThat(zone2).isLessThan(zone4);
        assertThat(zone4.compareTo(zone5)).isEqualTo(id1.compareTo(id2));
    }

    @Test
    public void toStringShouldContainFieldsValue() {
        final UUID id = UUID.randomUUID();
        final String zoneName = "Library";
        final int mpdInstancePortNumber = 789;
        final int version = 123434;
        final String actual = new ZoneDto(id, zoneName, mpdInstancePortNumber, version).toString();

        assertThat(actual).contains(id.toString());
        assertThat(actual).contains(zoneName);
        assertThat(actual).contains(Integer.toString(mpdInstancePortNumber));
        assertThat(actual).contains(Integer.toString(version));
    }
}
