package com.autelhome.multiroom.player;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PlaySongAtPositionTest {

    private static final UUID ZONE_ID = UUID.randomUUID();

    @Test
    public void getPosition() throws Exception {
        final int expected = 3;
        final int actual = new PlaySongAtPosition(UUID.randomUUID(), expected, 343).getPosition();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldBeEqual() throws Exception {
        final int originalVersion = 364734;
        final PlaySongAtPosition playSongAtPosition1 = new PlaySongAtPosition(ZONE_ID, 45, originalVersion);
        final PlaySongAtPosition playSongAtPosition2 = new PlaySongAtPosition(ZONE_ID, 45, originalVersion);

        assertThat(playSongAtPosition1).isEqualTo(playSongAtPosition1);
        assertThat(playSongAtPosition1).isEqualTo(playSongAtPosition2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final int originalVersion = 364734;
        final PlaySongAtPosition playSongAtPosition1 = new PlaySongAtPosition(ZONE_ID, 1, originalVersion);
        final PlaySongAtPosition playSongAtPosition2 = new PlaySongAtPosition(UUID.randomUUID(), 1, originalVersion);
        final PlaySongAtPosition playSongAtPosition3 = new PlaySongAtPosition(ZONE_ID, 2, originalVersion);
        final PlaySongAtPosition playSongAtPosition4 = new PlaySongAtPosition(ZONE_ID, 1, 8323);

        assertThat(playSongAtPosition1).isNotEqualTo(" ");
        assertThat(playSongAtPosition1).isNotEqualTo(null);
        assertThat(playSongAtPosition1).isNotEqualTo(playSongAtPosition2);
        assertThat(playSongAtPosition1).isNotEqualTo(playSongAtPosition3);
        assertThat(playSongAtPosition1).isNotEqualTo(playSongAtPosition4);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int originalVersion = 364734;
        final int hashCode1 = new PlaySongAtPosition(ZONE_ID, 72, originalVersion).hashCode();
        final int hashCode2 = new PlaySongAtPosition(ZONE_ID, 72, originalVersion).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int originalVersion = 364734;
        final int hashCode1 = new PlaySongAtPosition(ZONE_ID, 234, originalVersion).hashCode();
        final int hashCode2 = new PlaySongAtPosition(UUID.randomUUID(), 234, originalVersion).hashCode();
        final int hashCode3 = new PlaySongAtPosition(ZONE_ID, 567, originalVersion).hashCode();
        final int hashCode4 = new PlaySongAtPosition(ZONE_ID, 234, 53847).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
        assertThat(hashCode1).isNotEqualTo(hashCode4);
    }

    @Test
    public void toStringShouldContainFieldsValue() throws Exception {
        final UUID id = UUID.randomUUID();
        final int position = 987;
        final int originalVersion = 38408352;
        final String actual = new PlaySongAtPosition(id, position, originalVersion).toString();

        assertThat(actual).contains(id.toString());
        assertThat(actual).contains(String.valueOf(position));
        assertThat(actual).contains(Integer.toString(originalVersion));
    }
}