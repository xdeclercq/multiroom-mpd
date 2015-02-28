package com.autelhome.multiroom.player;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotEquals;

public class ChangePlayerStatusTest {

    @Test
    public void shouldBeEqual() throws Exception {
        final UUID id = UUID.randomUUID();
        final ChangePlayerStatus changePlayerStatus1 = new ChangePlayerStatus(id, PlayerStatus.PAUSED, 2);
        final ChangePlayerStatus changePlayerStatus2 = new ChangePlayerStatus(id, PlayerStatus.PAUSED, 2);

        assertThat(changePlayerStatus1).isEqualTo(changePlayerStatus1);
        assertThat(changePlayerStatus1).isEqualTo(changePlayerStatus2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final UUID id = UUID.randomUUID();
        final ChangePlayerStatus changePlayerStatus1 = new ChangePlayerStatus(id, PlayerStatus.PAUSED, 2);
        final ChangePlayerStatus changePlayerStatus2 = new ChangePlayerStatus(UUID.randomUUID(), PlayerStatus.PAUSED, 2);
        final ChangePlayerStatus changePlayerStatus3 = new ChangePlayerStatus(id, PlayerStatus.PLAYING, 2);
        final ChangePlayerStatus changePlayerStatus4 = new ChangePlayerStatus(id, PlayerStatus.PAUSED, 3);

        assertNotEquals(changePlayerStatus1, " ");
        assertNotEquals(changePlayerStatus1, null);
        assertThat(changePlayerStatus1).isNotEqualTo(changePlayerStatus2);
        assertThat(changePlayerStatus1).isNotEqualTo(changePlayerStatus3);
        assertThat(changePlayerStatus1).isNotEqualTo(changePlayerStatus4);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final UUID id = UUID.randomUUID();
        final int hashCode1 = new ChangePlayerStatus(id, PlayerStatus.PAUSED, 2).hashCode();
        final int hashCode2 = new ChangePlayerStatus(id, PlayerStatus.PAUSED, 2).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldBeDifferent() throws Exception {
        final UUID id = UUID.randomUUID();
        final int hashCode1 = new ChangePlayerStatus(id, PlayerStatus.PAUSED, 2).hashCode();
        final int hashCode2 = new ChangePlayerStatus(UUID.randomUUID(), PlayerStatus.PAUSED, 2).hashCode();
        final int hashCode3 = new ChangePlayerStatus(id, PlayerStatus.PLAYING, 2).hashCode();
        final int hashCode4 = new ChangePlayerStatus(id, PlayerStatus.PAUSED, 3).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
        assertThat(hashCode1).isNotEqualTo(hashCode4);
    }

    @Test
    public void toStringShouldContainFieldsValue() {
        final UUID id = UUID.randomUUID();
        final PlayerStatus playerStatus = PlayerStatus.PAUSED;
        final int originalVersion = 38408352;
        final String actual = new ChangePlayerStatus(id, playerStatus, originalVersion).toString();

        MatcherAssert.assertThat(actual, containsString(id.toString()));
        MatcherAssert.assertThat(actual, containsString(playerStatus.toString()));
        MatcherAssert.assertThat(actual, containsString(Integer.toString(originalVersion)));
    }


}