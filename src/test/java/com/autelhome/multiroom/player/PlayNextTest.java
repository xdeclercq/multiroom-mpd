package com.autelhome.multiroom.player;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayNextTest {

    private static final UUID ZONE_ID = UUID.randomUUID();

    @Test
    public void shouldBeEqual() throws Exception {
        final int originalVersion = 364734;
        final PlayNext playNext1 = new PlayNext(ZONE_ID, originalVersion);
        final PlayNext playNext2 = new PlayNext(ZONE_ID, originalVersion);

        assertThat(playNext1).isEqualTo(playNext1);
        assertThat(playNext1).isEqualTo(playNext2);
    }

    @Test
    public void shouldNotBeEqual() throws Exception {
        final int originalVersion = 364734;
        final PlayNext playNext1 = new PlayNext(ZONE_ID, originalVersion);
        final PlayNext playNext2 = new PlayNext(UUID.randomUUID(), originalVersion);
        final PlayNext playNext3 = new PlayNext(ZONE_ID,  8323);

        assertThat(playNext1).isNotEqualTo(" ");
        assertThat(playNext1).isNotEqualTo(null);
        assertThat(playNext1).isNotEqualTo(playNext2);
        assertThat(playNext1).isNotEqualTo(playNext3);
    }

    @Test
    public void hashCodeShouldBeTheSame() throws Exception {
        final int originalVersion = 364734;
        final int hashCode1 = new PlayNext(ZONE_ID, originalVersion).hashCode();
        final int hashCode2 = new PlayNext(ZONE_ID, originalVersion).hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() throws Exception {
        final int originalVersion = 364734;
        final int hashCode1 = new PlayNext(ZONE_ID, originalVersion).hashCode();
        final int hashCode2 = new PlayNext(UUID.randomUUID(), originalVersion).hashCode();
        final int hashCode3 = new PlayNext(ZONE_ID, 53847).hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
    }

    @Test
    public void toStringShouldContainFieldsValue() throws Exception {
        final UUID id = UUID.randomUUID();
        final int originalVersion = 38408352;
        final String actual = new PlayNext(id, originalVersion).toString();

        assertThat(actual).contains(PlayNext.class.getSimpleName().toString());
        assertThat(actual).contains(id.toString());
        assertThat(actual).contains(Integer.toString(originalVersion));
    }
}