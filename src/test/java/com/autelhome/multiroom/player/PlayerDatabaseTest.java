package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.InstanceAlreadyPresentException;
import com.autelhome.multiroom.util.InstanceNotFoundException;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerDatabaseTest {

    private static final String A_ZONE = "a zone";
    final PlayerDatabase testSubject = new PlayerDatabase();

    @Test
    public void addUpdateAndGetByZoneName() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final String zoneName = A_ZONE;
        final PlayerStatus playerStatus = PlayerStatus.PAUSED;
        testSubject.add(new PlayerDto(zoneId, zoneName, playerStatus));

        final Optional<PlayerDto> actual1 = testSubject.getByZoneName(zoneName);

        final Optional<PlayerDto> expected1 = Optional.of(new PlayerDto(zoneId, zoneName, playerStatus));
        assertThat(actual1).isEqualTo(expected1);

        final String zoneName2 = "another name";
        final PlayerDto player2 = new PlayerDto(zoneId, zoneName2, PlayerStatus.PLAYING);
        testSubject.update(player2);
        final Optional<PlayerDto> actual2 = testSubject.getByZoneName(zoneName2);
        assertThat(actual2).isEqualTo(Optional.of(player2));

        final Optional<PlayerDto> actual3 = testSubject.getByZoneName(zoneName);
        final Optional<PlayerDto> expected3 = Optional.<PlayerDto>empty();
        assertThat(actual3).isEqualTo(expected3);
    }

    @Test
    public void addAndGetByZoneId() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        final String zoneName = A_ZONE;
        final PlayerStatus playerStatus = PlayerStatus.PAUSED;
        testSubject.add(new PlayerDto(zoneId, zoneName, playerStatus));

        final Optional<PlayerDto> actual = testSubject.getByZoneId(zoneId);

        final Optional<PlayerDto> expected = Optional.of(new PlayerDto(zoneId, zoneName, playerStatus));
        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = InstanceAlreadyPresentException.class)
    public void addAlreadyExistingZoneWithSameId() throws Exception {
        final UUID zoneId = UUID.randomUUID();
        testSubject.add(new PlayerDto(zoneId, A_ZONE, PlayerStatus.PAUSED));
        testSubject.add(new PlayerDto(zoneId, "another zone name", PlayerStatus.PLAYING));
    }

    @Test(expected = InstanceAlreadyPresentException.class)
    public void addAlreadyExistingZoneWithSameName() throws Exception {
        testSubject.add(new PlayerDto(UUID.randomUUID(), A_ZONE, PlayerStatus.PAUSED));
        testSubject.add(new PlayerDto(UUID.randomUUID(), A_ZONE, PlayerStatus.PLAYING));
    }

    @Test(expected = InstanceNotFoundException.class)
    public void updateNotFound() throws Exception {
        testSubject.update(new PlayerDto(UUID.randomUUID(), "some zone", PlayerStatus.PAUSED));
    }
}