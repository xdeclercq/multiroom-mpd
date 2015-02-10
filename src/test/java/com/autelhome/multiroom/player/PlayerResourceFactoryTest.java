package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.Zone;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerResourceFactoryTest {

	private final PlayerProvider playerProvider = mock(PlayerProvider.class);
	private final PlayerRepresentationFactory playerRepresentationFactory = mock(PlayerRepresentationFactory.class);
    private final EventBus eventBus = mock(EventBus.class);
	private final PlayerResourceFactory testSubject = new PlayerResourceFactory(playerProvider, playerRepresentationFactory, eventBus);

	@Test
	public void newInstance() throws Exception {
		final Zone kitchen = new Zone("Kitchen");
		final Player player = new Player(kitchen, PlayerStatus.STOPPED);
		when(playerProvider.getPlayer(kitchen)).thenReturn(player);

		final PlayerResource expected = new PlayerResource(player, playerRepresentationFactory, eventBus);
		final PlayerResource actual = testSubject.newInstance(kitchen);

		assertThat(actual).isEqualTo(expected);
	}
}