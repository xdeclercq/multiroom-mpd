package com.autelhome.multiroom.zone;

import org.junit.Test;

import java.util.*;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZoneServiceTest {

    private static final String KITCHEN = "Kitchen";
    private final ZoneDatabase zoneDatabase = mock(ZoneDatabase.class);
    private final ZoneService testSubject = new ZoneService(zoneDatabase);


    @Test
    public void getAll() throws Exception {
        final SortedSet<ZoneDto> expected = new TreeSet<>();
        expected.add(new ZoneDto(UUID.randomUUID(), KITCHEN, 7912, 1));
        expected.add(new ZoneDto(UUID.randomUUID(), "Bedroom", 7912, 1));

        when(zoneDatabase.getAll()).thenReturn(expected);

        final SortedSet<ZoneDto> actual = testSubject.getAll();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getByName() throws Exception {
        final Optional<ZoneDto> expected = Optional.of(new ZoneDto(UUID.randomUUID(), KITCHEN, 7912, 1));
        when(zoneDatabase.getByName(KITCHEN)).thenReturn(expected);
        final Optional<ZoneDto> actual = testSubject.getByName(KITCHEN);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getByNameUnknownZone() throws Exception {
        final String zoneName = "zone3";
        when(zoneDatabase.getByName(zoneName)).thenReturn(Optional.<ZoneDto>empty());
        final Optional<ZoneDto> actual = testSubject.getByName(zoneName);

        assertThat(actual.isPresent()).isFalse();
    }

}