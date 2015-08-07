package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.errors.InvalidOperationException;
import com.autelhome.multiroom.player.*;
import com.autelhome.multiroom.playlist.ZonePlaylist;
import com.autelhome.multiroom.playlist.ZonePlaylistUpdated;
import com.autelhome.multiroom.song.Song;
import com.autelhome.multiroom.util.Event;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ZoneTest {

    private static final String NAME = "the zone";
    private static final String BEDROOM = "Bedroom";
    private static final String KITCHEN = "Kitchen";
    private static final String BATHROOM = "Bathroom";
    private static final ZonePlaylist PLAYLIST = new ZonePlaylist(Arrays.asList(new Song("a"), new Song("b")));


    @Test
    public void create() throws Exception {
        final UUID id = UUID.randomUUID();
        final Zone testSubject = new Zone(id, NAME, 12, PlayerStatus.PLAYING, PLAYLIST);
        final List<Event> actualUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(actualUncommittedChanges).hasSize(1);
        assertThat(actualUncommittedChanges.get(0)).isEqualTo(new ZoneCreated(id, NAME, 12, PlayerStatus.PLAYING, PLAYLIST));
    }


    @Test
    public void changePlayerStatus() throws Exception {
        final UUID id = UUID.randomUUID();
        final Zone testSubject = new Zone(id, NAME, 12, PlayerStatus.PLAYING, PLAYLIST);
        final List<Event> initialUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(initialUncommittedChanges).hasSize(1);
        final PlayerStatus newPlayerStatus = PlayerStatus.PAUSED;
        testSubject.changePlayerStatus(newPlayerStatus);
        final List<Event> actualUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(actualUncommittedChanges).hasSize(2);
        assertThat(actualUncommittedChanges.get(1)).isEqualTo(new PlayerStatusUpdated(id, newPlayerStatus));
    }

    @Test
    public void changeCurrentSong() throws Exception {
        final UUID id = UUID.randomUUID();
        final Zone testSubject = new Zone(id, NAME, 12, PlayerStatus.PLAYING, PLAYLIST);
        final List<Event> initialUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(initialUncommittedChanges).hasSize(1);
        final CurrentSong newCurrentSong = new CurrentSong(new Song("Song A"), 1);
        testSubject.changeCurrentSong(newCurrentSong);
        final List<Event> actualUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(actualUncommittedChanges).hasSize(2);
        assertThat(actualUncommittedChanges.get(1)).isEqualTo(new CurrentSongUpdated(id, newCurrentSong));
    }

    @Test
    public void playSong() throws Exception {
        final UUID id = UUID.randomUUID();
        final Zone testSubject = new Zone(id, NAME, 12, PlayerStatus.PLAYING, PLAYLIST);
        final List<Event> initialUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(initialUncommittedChanges).hasSize(1);
        testSubject.playSongAtPosition(1);
        final List<Event> actualUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(actualUncommittedChanges).hasSize(2);
        assertThat(actualUncommittedChanges.get(1)).isEqualTo(new SongAtPositionPlayed(id, new CurrentSong(new Song("b"), 1)));
    }

    @Test
    public void changePlaylist() throws Exception {
        final UUID id = UUID.randomUUID();
        final Zone testSubject = new Zone(id, NAME, 12, PlayerStatus.PLAYING, PLAYLIST);
        final List<Event> initialUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(initialUncommittedChanges).hasSize(1);
        final ZonePlaylist newPlaylist = new ZonePlaylist(Arrays.asList(new Song("Song A"), new Song("Song B")));
        testSubject.changePlaylist(newPlaylist);
        final List<Event> actualUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(actualUncommittedChanges).hasSize(2);
        assertThat(actualUncommittedChanges.get(1)).isEqualTo(new ZonePlaylistUpdated(id, newPlaylist));
    }

    @Test(expected = InvalidOperationException.class)
    public void changePlayerStatusWithSameStatus() throws Exception {
        final PlayerStatus newPlayerStatus = PlayerStatus.PAUSED;
        final Zone testSubject = new Zone(UUID.randomUUID(), NAME, 12, newPlayerStatus, PLAYLIST);
        testSubject.changePlayerStatus(newPlayerStatus);
    }


    @Test
    public void play() throws Exception {
        final UUID id = UUID.randomUUID();
        final Zone testSubject = new Zone(id, NAME, 12, PlayerStatus.PAUSED, PLAYLIST);
        final List<Event> initialUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(initialUncommittedChanges).hasSize(1);
        testSubject.play();
        final List<Event> actualUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(actualUncommittedChanges).hasSize(2);
        assertThat(actualUncommittedChanges.get(1)).isEqualTo(new Played(id));
    }

    @Test(expected = InvalidOperationException.class)
    public void playWhenPlaying() throws Exception {
        final Zone testSubject = new Zone(UUID.randomUUID(), NAME, 12, PlayerStatus.PLAYING, PLAYLIST);
        testSubject.play();
    }

    @Test
    public void pause() throws Exception {
        final UUID id = UUID.randomUUID();
        final Zone testSubject = new Zone(id, NAME, 12, PlayerStatus.PLAYING, PLAYLIST);
        final List<Event> initialUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(initialUncommittedChanges).hasSize(1);
        testSubject.pause();
        final List<Event> actualUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(actualUncommittedChanges).hasSize(2);
        assertThat(actualUncommittedChanges.get(1)).isEqualTo(new Paused(id));
    }

    @Test(expected = InvalidOperationException.class)
    public void pauseWhenPaused() throws Exception {
        final Zone testSubject = new Zone(UUID.randomUUID(), NAME, 12, PlayerStatus.PAUSED, PLAYLIST);
        testSubject.pause();
    }

    @Test(expected = InvalidOperationException.class)
    public void pauseWhenStopped() throws Exception {
        final Zone testSubject = new Zone(UUID.randomUUID(), NAME, 12, PlayerStatus.STOPPED, PLAYLIST);
        testSubject.pause();
    }

    @Test
    public void stop() throws Exception {
        final UUID id = UUID.randomUUID();
        final Zone testSubject = new Zone(id, NAME, 12, PlayerStatus.PLAYING, PLAYLIST);
        final List<Event> initialUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(initialUncommittedChanges).hasSize(1);
        testSubject.stop();
        final List<Event> actualUncommittedChanges = testSubject.getUncommittedChanges();
        assertThat(actualUncommittedChanges).hasSize(2);
        assertThat(actualUncommittedChanges.get(1)).isEqualTo(new Stopped(id));
    }

    @Test(expected = InvalidOperationException.class)
    public void stopWhenStopped() throws Exception {
        final Zone testSubject = new Zone(UUID.randomUUID(), NAME, 12, PlayerStatus.STOPPED, PLAYLIST);
        testSubject.stop();
    }

    @Test
    public void getMpdInstancePortNumber() throws Exception {
        final int expected = 12;
        final Zone testSubject = new Zone(UUID.randomUUID(), NAME, expected, PlayerStatus.PLAYING, PLAYLIST);
        final int actual = testSubject.getMpdInstancePortNumber();
        
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getName() throws Exception {
        final String expected = "this is a zone";
        final Zone testSubject = new Zone(UUID.randomUUID(), expected, 12, PlayerStatus.PLAYING, PLAYLIST);
        final int actual = testSubject.getMpdInstancePortNumber();

        assertThat(actual).isEqualTo(actual);
    }

    @Test
    public void loadFromHistory() throws Exception {
        final UUID id = UUID.randomUUID();
        final Zone testSubject = new Zone();
        testSubject.loadFromHistory(Arrays.asList(
                new ZoneCreated(id, NAME, 12, PlayerStatus.PLAYING, PLAYLIST),
                new Stopped(id),
                new Played(id),
                new Paused(id),
                new PlayerStatusUpdated(id, PlayerStatus.STOPPED)));
        final Zone expected = new Zone(id, NAME, 12, PlayerStatus.STOPPED, PLAYLIST);

        assertThat(testSubject).isEqualTo(expected);
    }

    @Test
    public void loadFromHistoryWithNoEvent() throws Exception {
        final Zone testSubject = new Zone();
        testSubject.loadFromHistory(Collections.emptyList());
        final Zone expected = new Zone();

        assertThat(testSubject).isEqualTo(expected);
    }

    @Test
    public void markChangesAsCommited() throws Exception {
        final UUID id = UUID.randomUUID();
        final Zone testSubject = new Zone(id, NAME, 12, PlayerStatus.STOPPED, PLAYLIST);
        final List<Event> initialUncommittedChanges = testSubject.getUncommittedChanges();
        
        assertThat(initialUncommittedChanges).hasSize(1);
        
        testSubject.markChangesAsCommited();
        final List<Event> actualUncommittedChanges = testSubject.getUncommittedChanges();
        
        assertThat(actualUncommittedChanges).isEmpty();
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final UUID id = UUID.randomUUID();
        final Zone zone1 = new Zone(id, BEDROOM, 789, PlayerStatus.PLAYING, PLAYLIST);
        final Zone zone2 = new Zone(id, BEDROOM, 789, PlayerStatus.PLAYING, PLAYLIST);

        assertThat(zone1).isEqualTo(zone1);
        assertThat(zone1).isEqualTo(zone2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final UUID id1 = UUID.randomUUID();
        final Zone zone1 = new Zone(id1, KITCHEN, 789, PlayerStatus.PLAYING, PLAYLIST);
        final Zone zone2 = new Zone(id1, BATHROOM, 789, PlayerStatus.PLAYING, PLAYLIST);
        final UUID id2 = UUID.randomUUID();
        final Zone zone3 = new Zone(id2, KITCHEN, 789, PlayerStatus.PLAYING, PLAYLIST);
        final Zone zone4 = new Zone(id2, KITCHEN, 123, PlayerStatus.PLAYING, PLAYLIST);
        final Zone zone5 = new Zone(id2, KITCHEN, 123, PlayerStatus.PAUSED, PLAYLIST);
        final Zone zone6 = new Zone(id2, KITCHEN, 123, PlayerStatus.PLAYING, PLAYLIST);
        final Paused paused = new Paused(id2);
        paused.setVersion(777);
        zone6.loadFromHistory(Arrays.asList(paused));
        final Zone zone7 = new Zone(null, KITCHEN, 123, PlayerStatus.PLAYING, PLAYLIST);
        final Zone zone8 = new Zone(id2, null, 123, PlayerStatus.PLAYING, PLAYLIST);

        assertThat(zone1).isNotEqualTo(" ");
        assertThat(zone1).isNotEqualTo(null);
        assertThat(zone1).isNotEqualTo(zone2);
        assertThat(zone3).isNotEqualTo(zone1);
        assertThat(zone4).isNotEqualTo(zone3);
        assertThat(zone4).isNotEqualTo(zone5);
        assertThat(zone5).isNotEqualTo(zone6);
        assertThat(zone6).isNotEqualTo(zone7);
        assertThat(zone7).isNotEqualTo(zone6);
        assertThat(zone8).isNotEqualTo(zone6);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final UUID id = UUID.randomUUID();
        final int hashCode1 = new Zone(id, "Hall", 789, PlayerStatus.PLAYING, PLAYLIST).hashCode();
        final int hashCode2 = new Zone(id, "Hall", 789, PlayerStatus.PLAYING, PLAYLIST).hashCode();
        final int hashCode3 = new Zone(id, null, 789, PlayerStatus.PLAYING, PLAYLIST).hashCode();
        final int hashCode4 = new Zone(id, null, 789, PlayerStatus.PLAYING, PLAYLIST).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
        assertThat(hashCode3).isEqualTo(hashCode4);
    }

    @Test
    public void hashCodeShouldBeDifferent() throws Exception {
        final UUID id = UUID.randomUUID();
        final int hashCode1 = new Zone(id, "Music room", 789, PlayerStatus.PLAYING, PLAYLIST).hashCode();
        final int hashCode2 = new Zone(id, BATHROOM, 789, PlayerStatus.PLAYING, PLAYLIST).hashCode();
        final int hashCode3 = new Zone(id, BATHROOM, 790, PlayerStatus.PLAYING, PLAYLIST).hashCode();
        final int hashCode4 = new Zone(id, BATHROOM, 790, PlayerStatus.STOPPED, PLAYLIST).hashCode();
        final int hashCode5 = new Zone(UUID.randomUUID(), BATHROOM, 790, PlayerStatus.STOPPED, PLAYLIST).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode2).isNotEqualTo(hashCode3);
        assertThat(hashCode3).isNotEqualTo(hashCode4);
        assertThat(hashCode4).isNotEqualTo(hashCode5);
    }

    @Test
    public void toStringShouldContainFieldsValue() {
        final UUID id = UUID.randomUUID();
        final String zoneName = "Library";
        final int mpdInstancePortNumber = 789;
        final PlayerStatus playerStatus = PlayerStatus.PLAYING;
        final Zone zone = new Zone(id, zoneName, mpdInstancePortNumber, playerStatus, PLAYLIST);
        final String actual = zone.toString();

        assertThat(actual).contains(id.toString());
        assertThat(actual).contains(zoneName);
        assertThat(actual).contains(Integer.toString(mpdInstancePortNumber));
        assertThat(actual).contains(Integer.toString(zone.getVersion()));
        assertThat(actual).contains("version");
        assertThat(actual).contains(playerStatus.toString());
    }
}