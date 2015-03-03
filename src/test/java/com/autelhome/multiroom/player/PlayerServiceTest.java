package com.autelhome.multiroom.player;

import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerServiceTest {

    private final PlayerDatabase playerDatabase = mock(PlayerDatabase.class);
    private final PlayerService testSubject = new PlayerService(playerDatabase);

    @Test
    public void getPlayerByZoneName() throws Exception {
        final String zoneName = "some zone";

        final Optional<PlayerDto> expected = Optional.of(new PlayerDto(UUID.randomUUID(), zoneName, PlayerStatus.STOPPED));
        when(playerDatabase.getByZoneName(zoneName)).thenReturn(expected);
        final Optional<PlayerDto> actual = testSubject.getPlayerByZoneName(zoneName);

        assertThat(actual).isEqualTo(expected);
    }
}