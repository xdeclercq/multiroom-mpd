package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.player.CurrentSong;
import com.autelhome.multiroom.player.PlayerStatus;
import com.autelhome.multiroom.playlist.ZonePlaylist;
import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.util.EventBus;
import com.autelhome.multiroom.zone.ZoneRepository;
import org.bff.javampd.MPD;
import org.bff.javampd.Player;
import org.bff.javampd.Playlist;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.objects.MPDSong;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MPDGatewayTest {

    private static final int MPD_INSTANCE_PORT_NUMBER = 12;
    private static final String SONG_A = "Song A";
    private static final String SONG_B = "Song B";

    private final MPDProvider mpdProvider = mock(MPDProvider.class);
    private final EventBus eventBus = mock(EventBus.class);
    private final ZoneRepository zoneRepository = mock(ZoneRepository.class);
    private final MPDGateway testSubject = new MPDGateway(mpdProvider, eventBus, zoneRepository);
    private final MPD mpd = mock(MPD.class);
    private final Player mpdPlayer = mock(Player.class);


    @Before
    public void setUp() {
        when(mpdProvider.getMPD(MPD_INSTANCE_PORT_NUMBER)).thenReturn(mpd);
        when(mpd.getPlayer()).thenReturn(mpdPlayer);
    }

    @Test
    public void getPlayerStatus() throws Exception {
        when(mpdPlayer.getStatus()).thenReturn(Player.Status.STATUS_PLAYING);
        final PlayerStatus actual = testSubject.getPlayerStatus(MPD_INSTANCE_PORT_NUMBER);

        assertThat(actual).isEqualTo(PlayerStatus.PLAYING);
    }

    @Test
    public void getPlayerStatusWithException() throws Exception {
        doThrow(MPDPlayerException.class).when(mpdPlayer).getStatus();
        final PlayerStatus actual = testSubject.getPlayerStatus(MPD_INSTANCE_PORT_NUMBER);

        assertThat(actual).isEqualTo(PlayerStatus.UNKNOWN);
    }

    @Test
    public void playSongAtPosition() throws Exception {
        when(mpdProvider.getMPD(MPD_INSTANCE_PORT_NUMBER)).thenReturn(mpd);

        final Playlist mpdPlaylist = mock(Playlist.class);

        when(mpd.getPlaylist()).thenReturn(mpdPlaylist);

        final MPDSong mpdSongA = new MPDSong();
        mpdSongA.setTitle(SONG_A);
        final MPDSong mpdSongB = new MPDSong();
        mpdSongB.setTitle(SONG_B);
        when(mpdPlaylist.getSongList()).thenReturn(Arrays.asList(mpdSongA, mpdSongB));

        testSubject.playSongAtPosition(MPD_INSTANCE_PORT_NUMBER, 1);
        verify(mpdPlayer).playId(mpdSongA);
    }


    @Test
    public void playSongAtPositionWithException() throws Exception {
        when(mpdProvider.getMPD(MPD_INSTANCE_PORT_NUMBER)).thenReturn(mpd);

        final Playlist mpdPlaylist = mock(Playlist.class);

        when(mpd.getPlaylist()).thenReturn(mpdPlaylist);

        final MPDSong mpdSongA = new MPDSong();
        mpdSongA.setTitle(SONG_A);
        final MPDSong mpdSongB = new MPDSong();
        mpdSongB.setTitle(SONG_B);
        when(mpdPlaylist.getSongList()).thenReturn(Arrays.asList(mpdSongA, mpdSongB));

        doThrow(MPDPlayerException.class).when(mpdPlayer).playId(mpdSongA);
        testSubject.playSongAtPosition(MPD_INSTANCE_PORT_NUMBER, 1);
        verify(mpdPlayer).playId(mpdSongA);
    }

    @Test
    public void playSongAtPositionTooBig() throws Exception {
        when(mpdProvider.getMPD(MPD_INSTANCE_PORT_NUMBER)).thenReturn(mpd);

        final Playlist mpdPlaylist = mock(Playlist.class);

        when(mpd.getPlaylist()).thenReturn(mpdPlaylist);

        final MPDSong mpdSongA = new MPDSong();
        mpdSongA.setTitle(SONG_A);
        final MPDSong mpdSongB = new MPDSong();
        mpdSongB.setTitle(SONG_B);
        when(mpdPlaylist.getSongList()).thenReturn(Arrays.asList(mpdSongA, mpdSongB));

        testSubject.playSongAtPosition(MPD_INSTANCE_PORT_NUMBER, 3);
        verify(mpdPlayer, never()).playId(any());
    }

    @Test
    public void playSongAtPositionTooSmall() throws Exception {
        when(mpdProvider.getMPD(MPD_INSTANCE_PORT_NUMBER)).thenReturn(mpd);

        final Playlist mpdPlaylist = mock(Playlist.class);

        when(mpd.getPlaylist()).thenReturn(mpdPlaylist);

        final MPDSong mpdSongA = new MPDSong();
        mpdSongA.setTitle(SONG_A);
        final MPDSong mpdSongB = new MPDSong();
        mpdSongB.setTitle(SONG_B);
        when(mpdPlaylist.getSongList()).thenReturn(Arrays.asList(mpdSongA, mpdSongB));

        testSubject.playSongAtPosition(MPD_INSTANCE_PORT_NUMBER, 0);
        verify(mpdPlayer, never()).playId(any());
    }


    @Test
    public void play() throws Exception {
        testSubject.play(MPD_INSTANCE_PORT_NUMBER);
        verify(mpdPlayer).play();
    }

    @Test
    public void playWithException() throws Exception {
        doThrow(MPDPlayerException.class).when(mpdPlayer).play();
        testSubject.play(MPD_INSTANCE_PORT_NUMBER);
        verify(mpdPlayer).play();
    }

    @Test
    public void pause() throws Exception {
        testSubject.pause(MPD_INSTANCE_PORT_NUMBER);
        verify(mpdPlayer).pause();
    }

    @Test
    public void pauseWithException() throws Exception {
        doThrow(MPDPlayerException.class).when(mpdPlayer).pause();
        testSubject.pause(MPD_INSTANCE_PORT_NUMBER);
        verify(mpdPlayer).pause();
    }

    @Test
    public void stop() throws Exception {
        testSubject.stop(MPD_INSTANCE_PORT_NUMBER);
        verify(mpdPlayer).stop();
    }

    @Test
    public void stopWithException() throws Exception {
        doThrow(MPDPlayerException.class).when(mpdPlayer).stop();
        testSubject.stop(MPD_INSTANCE_PORT_NUMBER);
        verify(mpdPlayer).stop();
    }

    @Test
    public void getZonePlaylist() throws Exception {
        final ZonePlaylist expected = new ZonePlaylist(Arrays.asList(new Song(SONG_A), new Song("Song B")));

        when(mpdProvider.getMPD(MPD_INSTANCE_PORT_NUMBER)).thenReturn(mpd);

        final Playlist mpdPlaylist = mock(Playlist.class);

        when(mpd.getPlaylist()).thenReturn(mpdPlaylist);

        final MPDSong mpdSongA = new MPDSong();
        mpdSongA.setTitle(SONG_A);
        final MPDSong mpdSongB = new MPDSong();
        mpdSongB.setTitle(SONG_B);
        when(mpdPlaylist.getSongList()).thenReturn(Arrays.asList(mpdSongA, mpdSongB));

        final ZonePlaylist actual = testSubject.getZonePlaylist(MPD_INSTANCE_PORT_NUMBER);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getCurrentSong() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        when(mpdProvider.getMPD(MPD_INSTANCE_PORT_NUMBER)).thenReturn(mpd);

        when(mpd.getPlayer()).thenReturn(mpdPlayer);

        final MPDSong mpdSongA = new MPDSong();
        mpdSongA.setTitle(SONG_A);
        mpdSongA.setPosition(3);

        when(mpdPlayer.getCurrentSong()).thenReturn(mpdSongA);

        final Optional<CurrentSong> actual = testSubject.getCurrentSong(MPD_INSTANCE_PORT_NUMBER);

        final Optional<CurrentSong> expected = Optional.of(new CurrentSong(new Song(SONG_A), 4));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getCurrentSongWithNullSong() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        when(mpdProvider.getMPD(MPD_INSTANCE_PORT_NUMBER)).thenReturn(mpd);

        when(mpd.getPlayer()).thenReturn(mpdPlayer);

        when(mpdPlayer.getCurrentSong()).thenReturn(null);

        final Optional<CurrentSong> actual = testSubject.getCurrentSong(MPD_INSTANCE_PORT_NUMBER);

        final Optional<CurrentSong> expected = Optional.empty();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getCurrentSongWithMPDPlayerException() throws Exception {
        final Player mpdPlayer = mock(Player.class);
        when(mpdProvider.getMPD(MPD_INSTANCE_PORT_NUMBER)).thenReturn(mpd);

        when(mpd.getPlayer()).thenReturn(mpdPlayer);

        when(mpdPlayer.getCurrentSong()).thenThrow(MPDPlayerException.class);

        final Optional<CurrentSong> actual = testSubject.getCurrentSong(MPD_INSTANCE_PORT_NUMBER);

        final Optional<CurrentSong> expected = Optional.empty();

        assertThat(actual).isEqualTo(expected);
    }

}