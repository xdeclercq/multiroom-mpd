package com.autelhome.multiroom.player;

import com.autelhome.multiroom.mpd.MPDPool;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneConfiguration;
import com.autelhome.multiroom.zone.ZonesConfiguration;
import org.bff.javampd.exception.MPDPlayerException;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerProviderTest {


    private static final String KITCHEN = "Kitchen";

    @Test
    public void getPlayer() throws Exception {
        final org.bff.javampd.Player mpdPlayer = mock(org.bff.javampd.Player.class);
        final Zone kitchen = new Zone(KITCHEN);
        final MPDPool mpdPool = mock(MPDPool.class);
        when(mpdPool.getMPDPlayer(kitchen)).thenReturn(mpdPlayer);
        when(mpdPlayer.getStatus()).thenReturn(org.bff.javampd.Player.Status.STATUS_PLAYING);
        final ZonesConfiguration zonesConfiguration = new ZonesConfiguration();
        zonesConfiguration.add(new ZoneConfiguration(KITCHEN, 9873));
        final PlayerProvider testSubject = new PlayerProvider(zonesConfiguration, mpdPool);

        final Player expected = new Player(kitchen, PlayerStatus.PLAYING);

        final Player actual = testSubject.getPlayer(KITCHEN);

        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = PlayerException.class)
    public void newPlayerProviderShouldThrowExceptionWhenNoConnectionToMPDServer() throws Exception {
        final org.bff.javampd.Player mpdPlayer = mock(org.bff.javampd.Player.class);
        final Zone kitchen = new Zone(KITCHEN);
        final MPDPool mpdPool = mock(MPDPool.class);
        when(mpdPool.getMPDPlayer(kitchen)).thenReturn(mpdPlayer);
        when(mpdPlayer.getStatus()).thenThrow(new MPDPlayerException("No connection!"));
        final ZonesConfiguration zonesConfiguration = new ZonesConfiguration();
        zonesConfiguration.add(new ZoneConfiguration(KITCHEN, 9873));

        new PlayerProvider(zonesConfiguration, mpdPool);
    }
}