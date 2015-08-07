package com.autelhome.multiroom.player;

import com.autelhome.multiroom.mpd.MPDGateway;
import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.zone.Zone;
import com.autelhome.multiroom.zone.ZoneRepository;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

/**
 * @author xdeclercq
 */
public class PlayerCommandHandlersTest {

    private static final UUID ZONE_ID = UUID.randomUUID();
    private static final int MPD_INSTANCE_PORT_NUMBER = 1234;
    private final MPDGateway mpdGateway = mock(MPDGateway.class);
    private final ZoneRepository zoneRepository = mock(ZoneRepository.class);
    private final PlayerCommandHandlers testSubject = new PlayerCommandHandlers(mpdGateway, zoneRepository);

    @Test
    public void handlePlay() throws Exception {
        final Zone zone = mock(Zone.class);
        when(zone.getMpdInstancePortNumber()).thenReturn(MPD_INSTANCE_PORT_NUMBER);
        when(zoneRepository.getById(ZONE_ID)).thenReturn(zone);
        final int mpdInstancePortNumber = 12;
        when(zone.getMpdInstancePortNumber()).thenReturn(mpdInstancePortNumber);
        testSubject.handlePlay(new Play(ZONE_ID, 123));

        verify(mpdGateway, times(1)).play(mpdInstancePortNumber);
        verify(zoneRepository, times(1)).save(zone, 123);
    }

    @Test
    public void handlePause() throws Exception {
        final Zone zone = mock(Zone.class);
        when(zone.getMpdInstancePortNumber()).thenReturn(MPD_INSTANCE_PORT_NUMBER);
        when(zoneRepository.getById(ZONE_ID)).thenReturn(zone);
        final int mpdInstancePortNumber = 12;
        when(zone.getMpdInstancePortNumber()).thenReturn(mpdInstancePortNumber);
        testSubject.handlePause(new Pause(ZONE_ID, 123));

        verify(mpdGateway, times(1)).pause(mpdInstancePortNumber);
        verify(zoneRepository, times(1)).save(zone, 123);
    }

    @Test
    public void handleStop() throws Exception {
        final Zone zone = mock(Zone.class);
        when(zone.getMpdInstancePortNumber()).thenReturn(MPD_INSTANCE_PORT_NUMBER);
        when(zoneRepository.getById(ZONE_ID)).thenReturn(zone);
        final int mpdInstancePortNumber = 12;
        when(zone.getMpdInstancePortNumber()).thenReturn(mpdInstancePortNumber);
        testSubject.handleStop(new Stop(ZONE_ID, 123));

        verify(mpdGateway, times(1)).stop(mpdInstancePortNumber);
        verify(zoneRepository, times(1)).save(zone, 123);
    }

    @Test
    public void handleChangePlayerStatus() throws Exception {
        final Zone zone = mock(Zone.class);
        when(zoneRepository.getById(ZONE_ID)).thenReturn(zone);
        testSubject.handleChangePlayerStatus(new ChangePlayerStatus(ZONE_ID, PlayerStatus.STOPPED, 123));
        verify(zoneRepository, times(1)).save(zone, 123);
    }

    @Test
    public void handleChangeCurrentSong() throws Exception {
        final Zone zone = mock(Zone.class);
        when(zoneRepository.getById(ZONE_ID)).thenReturn(zone);
        testSubject.handleChangeCurrentSong(new ChangeCurrentSong(ZONE_ID, new CurrentSong(new Song("Song A"), 1), 123));
        verify(zoneRepository, times(1)).save(zone, 123);
    }

    @Test
    public void handlePlaySong() throws Exception {
        final Zone zone = mock(Zone.class);
        when(zoneRepository.getById(ZONE_ID)).thenReturn(zone);
        final int mpdInstancePortNumber = 12;
        when(zone.getMpdInstancePortNumber()).thenReturn(mpdInstancePortNumber);

        testSubject.handlePlaySong(new PlaySongAtPosition(ZONE_ID, 1, 123));

        verify(zoneRepository, times(1)).save(zone, 123);
        verify(mpdGateway, times(1)).playSongAtPosition(mpdInstancePortNumber, 1);
    }
}