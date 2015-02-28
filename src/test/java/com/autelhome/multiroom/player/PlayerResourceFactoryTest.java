package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.ZoneDto;
import org.junit.Test;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class PlayerResourceFactoryTest {

	private final PlayerRepresentationFactory playerRepresentationFactory = mock(PlayerRepresentationFactory.class);
    private final EventBus eventBus = mock(EventBus.class);
	private final PlayerResourceFactory testSubject = new PlayerResourceFactory(playerRepresentationFactory, eventBus);

	@Test
	public void newInstance() throws Exception {
		final ZoneDto kitchen = new ZoneDto(UUID.randomUUID(), "Kitchen", 7912, 1);

		final PlayerResource expected = new PlayerResource(kitchen, playerRepresentationFactory, eventBus);
		final PlayerResource actual = testSubject.newInstance(kitchen);

		assertThat(actual).isEqualTo(expected);
	}
}