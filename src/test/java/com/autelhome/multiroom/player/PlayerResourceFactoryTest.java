package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.ZoneDto;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerResourceFactoryTest {

    private final PlayerService playerService = mock(PlayerService.class);
	private final PlayerRepresentationFactory playerRepresentationFactory = mock(PlayerRepresentationFactory.class);
    private final EventBus eventBus = mock(EventBus.class);
	private final PlayerResourceFactory testSubject = new PlayerResourceFactory(playerService, playerRepresentationFactory, eventBus);

	@Test
	public void newInstance() throws Exception {
        final String zoneName = "Kitchen";
        final UUID zoneId = UUID.randomUUID();
        final ZoneDto kitchen = new ZoneDto(zoneId, zoneName, 7912, 1);

        when(playerService.getPlayerByZoneName(zoneName)).thenReturn(Optional.of(new PlayerDto(zoneId, zoneName, PlayerStatus.PAUSED)));
		final PlayerResource expected = new PlayerResource(kitchen, playerService, playerRepresentationFactory, eventBus);
		final PlayerResource actual = testSubject.newInstance(kitchen);

		assertThat(actual).isEqualTo(expected);
	}
}