package com.autelhome.multiroom.player;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayTest {

    private static final UUID ZONE_ID = UUID.randomUUID();

    @Test
    public void shouldBeEqual() throws Exception {
        final int originalVersion = 364734;
        final Play play1 = new Play(ZONE_ID, originalVersion);
        final Play play2 = new Play(ZONE_ID, originalVersion);

        assertThat(play1).isEqualTo(play1);
        assertThat(play1).isEqualTo(play2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final int originalVersion = 364734;
        final Play play1 = new Play(ZONE_ID, originalVersion);
        final Play play2 = new Play(UUID.randomUUID(), originalVersion);
        final Play play3 = new Play(ZONE_ID,  8323);

        assertThat(play1).isNotEqualTo(" ");
        assertThat(play1).isNotEqualTo(null);
        assertThat(play1).isNotEqualTo(play2);
        assertThat(play1).isNotEqualTo(play3);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int originalVersion = 364734;
        final int hashCode1 = new Play(ZONE_ID, originalVersion).hashCode();
        final int hashCode2 = new Play(ZONE_ID, originalVersion).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int originalVersion = 364734;
        final int hashCode1 = new Play(ZONE_ID, originalVersion).hashCode();
        final int hashCode2 = new Play(UUID.randomUUID(), originalVersion).hashCode();
        final int hashCode3 = new Play(ZONE_ID, 53847).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
    }

    @Test
    public void toStringShouldContainFieldsValue() throws Exception {
        final UUID id = UUID.randomUUID();
        final int originalVersion = 38408352;
        final String actual = new Play(id, originalVersion).toString();

        assertThat(actual).contains(Play.class.getSimpleName());
        assertThat(actual).contains(id.toString());
        assertThat(actual).contains(Integer.toString(originalVersion));
    }
}