package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.player.PlayerStatus;
import com.autelhome.multiroom.playlist.ZonePlaylist;
import com.autelhome.multiroom.song.Song;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class ZoneCreatedTest {

    private static final String BEDROOM = "Bedroom";
    private static final String KITCHEN = "Kitchen";
    private static final String BATHROOM = "Bathroom";
    private static final ZonePlaylist PLAYLIST = new ZonePlaylist(Arrays.asList(new Song("a"), new Song("b")));

    @Test
    public void shouldBeEqual() throws Exception {
        final UUID id = UUID.randomUUID();
        final ZoneCreated zoneCreated1 = new ZoneCreated(id, BEDROOM, 789, PlayerStatus.PLAYING, PLAYLIST);
        final ZoneCreated zoneCreated2 = new ZoneCreated(id, BEDROOM, 789, PlayerStatus.PLAYING, PLAYLIST);

        assertThat(zoneCreated1).isEqualTo(zoneCreated1);
        assertThat(zoneCreated1).isEqualTo(zoneCreated2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final UUID id1 = UUID.randomUUID();
        final ZoneCreated zoneCreated1 = new ZoneCreated(id1, KITCHEN, 789, PlayerStatus.PLAYING, PLAYLIST);
        final ZoneCreated zoneCreated2 = new ZoneCreated(id1, BATHROOM, 789, PlayerStatus.PLAYING, PLAYLIST);
        final UUID id2 = UUID.randomUUID();
        final ZoneCreated zoneCreated3 = new ZoneCreated(id2, KITCHEN, 789, PlayerStatus.PLAYING, PLAYLIST);
        final ZoneCreated zoneCreated4 = new ZoneCreated(id2, KITCHEN, 123, PlayerStatus.PLAYING, PLAYLIST);
        final ZoneCreated zoneCreated5 = new ZoneCreated(id2, KITCHEN, 123, PlayerStatus.PAUSED, PLAYLIST);
        final ZoneCreated zoneCreated6 = new ZoneCreated(id2, KITCHEN, 123, PlayerStatus.PAUSED, new ZonePlaylist(Arrays.asList(new Song("Song 1"))));

        assertThat(zoneCreated1).isNotEqualTo(" ");
        assertThat(zoneCreated1).isNotEqualTo(null);
        assertThat(zoneCreated1).isNotEqualTo(zoneCreated2);
        assertThat(zoneCreated3).isNotEqualTo(zoneCreated1);
        assertThat(zoneCreated4).isNotEqualTo(zoneCreated3);
        assertThat(zoneCreated4).isNotEqualTo(zoneCreated5);
        assertThat(zoneCreated5).isNotEqualTo(zoneCreated6);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final UUID id = UUID.randomUUID();
        final int hashCode1 = new ZoneCreated(id, "Hall", 789, PlayerStatus.PLAYING, PLAYLIST).hashCode();
        final int hashCode2 = new ZoneCreated(id, "Hall", 789, PlayerStatus.PLAYING, PLAYLIST).hashCode();
        final int hashCode3 = new ZoneCreated(id, null, 789, PlayerStatus.PLAYING, PLAYLIST).hashCode();
        final int hashCode4 = new ZoneCreated(id, null, 789, PlayerStatus.PLAYING, PLAYLIST).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
        assertThat(hashCode3).isEqualTo(hashCode4);
    }

    @Test
    public void hashCodeShouldBeDifferent() throws Exception {
        final UUID id = UUID.randomUUID();
        final int hashCode1 = new ZoneCreated(id, "Music room", 789, PlayerStatus.PLAYING, PLAYLIST).hashCode();
        final int hashCode2 = new ZoneCreated(id, BATHROOM, 789, PlayerStatus.PLAYING, PLAYLIST).hashCode();
        final int hashCode3 = new ZoneCreated(id, BATHROOM, 790, PlayerStatus.PLAYING, PLAYLIST).hashCode();
        final int hashCode4 = new ZoneCreated(id, BATHROOM, 790, PlayerStatus.STOPPED, PLAYLIST).hashCode();
        final int hashCode5 = new ZoneCreated(UUID.randomUUID(), BATHROOM, 790, PlayerStatus.STOPPED, PLAYLIST).hashCode();
        final int hashCode6 = new ZoneCreated(UUID.randomUUID(), BATHROOM, 790, PlayerStatus.STOPPED, new ZonePlaylist()).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode2).isNotEqualTo(hashCode3);
        assertThat(hashCode3).isNotEqualTo(hashCode4);
        assertThat(hashCode4).isNotEqualTo(hashCode5);
        assertThat(hashCode5).isNotEqualTo(hashCode6);
    }

}