package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.util.InstanceAlreadyPresentException;
import com.autelhome.multiroom.util.InstanceNotFoundException;
import org.junit.Test;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ZoneDatabaseTest {

    private static final String MY_ZONE = "my zone";
    private final ZoneDatabase testSubject = new ZoneDatabase();

    @Test
    public void addUpdateAndGet() throws Exception {
        final SortedSet<ZoneDto> initialZones = testSubject.getAll();
        assertThat(initialZones).isEmpty();
        final String name = MY_ZONE;
        final ZoneDto zoneDto1 = new ZoneDto(UUID.randomUUID(), name, 1234, 1);
        final UUID id2 = UUID.randomUUID();
        final ZoneDto zoneDto2 = new ZoneDto(id2, "another zone", 1234, 1);
        testSubject.add(zoneDto1);
        testSubject.add(zoneDto2);

        assertThat(testSubject.getAll()).hasSize(2);

        final ZoneDto zoneDto3 = new ZoneDto(id2, "new zone name", 4321, 2);
        testSubject.update(zoneDto3);

        final SortedSet<ZoneDto> actual1 = testSubject.getAll();
        assertThat(actual1).hasSize(2);
        final SortedSet<ZoneDto> expected1 = new TreeSet<>();
        expected1.add(zoneDto1);
        expected1.add(zoneDto3);
        assertThat(actual1).isEqualTo(expected1);

        final Optional<ZoneDto> actual2 = testSubject.getByName(name);
        assertThat(actual2).isEqualTo(Optional.of(zoneDto1));

        final Optional<ZoneDto> actual3 = testSubject.getById(id2);
        assertThat(actual3).isEqualTo(Optional.of(zoneDto3));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void addToInitialZones() throws Exception {
        final SortedSet<ZoneDto> initialZones = testSubject.getAll();
        initialZones.add(new ZoneDto(UUID.randomUUID(), "kitchen", 349, 1));
    }

    @Test(expected = InstanceAlreadyPresentException.class)
    public void addAlreadyExistingZoneWithSameId() throws Exception {
        final UUID id = UUID.randomUUID();
        final ZoneDto zoneDto1 = new ZoneDto(id, MY_ZONE, 1234, 1);
        final ZoneDto zoneDto2 = new ZoneDto(id, "another zone", 1234, 1);
        testSubject.add(zoneDto1);
        testSubject.add(zoneDto2);
    }

    @Test(expected = InstanceAlreadyPresentException.class)
    public void addAlreadyExistingZoneWithSameName() throws Exception {
        final ZoneDto zoneDto1 = new ZoneDto(UUID.randomUUID(), MY_ZONE, 1234, 1);
        final ZoneDto zoneDto2 = new ZoneDto(UUID.randomUUID(), MY_ZONE, 998, 23);
        testSubject.add(zoneDto1);
        testSubject.add(zoneDto2);
    }

    @Test(expected = InstanceNotFoundException.class)
    public void updateNotFound() throws Exception {
        testSubject.update(new ZoneDto(UUID.randomUUID(), MY_ZONE, 1234, 1));
    }

}