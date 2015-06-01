package com.autelhome.multiroom.player;

public class PlayerTest {

    private static final String KITCHEN = "Kitchen";
    private static final String BATHROOM = "Bathroom";

//    @Test
//    public void getZone() throws Exception {
//        final Zone expected = new Zone(KITCHEN);
//        final Player player = new Player(UUID.randomUUID(), expected);
//        final Zone actual = player.getZone();
//
//        assertThat(actual).isEqualTo(expected);
//    }
//
//    @Test
//    public void getStatus() throws Exception {
//        final PlayerStatus expected = PlayerStatus.PLAYING;
//        final Player player = new Player(new Zone(KITCHEN), expected);
//        final PlayerStatus actual = player.getStatus();
//
//        assertThat(actual).isEqualTo(expected);
//    }
//
//    @Test
//    public void shouldBeEqual() throws Exception {
//        final Player player1 = new Player(new Zone(KITCHEN), PlayerStatus.PLAYING);
//        final Player player2 = new Player(new Zone(KITCHEN), PlayerStatus.PLAYING);
//        final Player player3 = new Player(null, PlayerStatus.PLAYING);
//        final Player player4 = new Player(null, PlayerStatus.PLAYING);
//        final Player player5 = new Player(new Zone(BATHROOM), null);
//        final Player player6 = new Player(new Zone(BATHROOM), null);
//
//        assertThat(player1).isEqualTo(player1);
//        assertThat(player1).isEqualTo(player2);
//        assertThat(player3).isEqualTo(player4);
//        assertThat(player5).isEqualTo(player6);
//    }
//
//    @Test
//    @SuppressFBWarnings("EC_UNRELATED_TYPES")
//    public void shouldNotBeEqual() throws Exception {
//        final Player player1 = new Player(new Zone(KITCHEN), PlayerStatus.PLAYING);
//        final Player player2 = new Player(null, PlayerStatus.PLAYING);
//        final Player player3 = new Player(new Zone(BATHROOM), null);
//
//        assertThat(player1).isNotEqualTo(player2);
//        assertThat(player2).isNotEqualTo(player1);
//        assertThat(player1).isNotEqualTo(player3);
//        assertThat(player2).isNotEqualTo(player3);
//        assertThat(player1.equals(null)).isFalse(); // NOPMD
//        assertThat(player1.equals(" ")).isFalse(); // NOPMD
//    }
//
//    @Test
//    public void hashCodeShouldBeTheSame() throws Exception {
//        final int hashCode1 = new Player(new Zone(KITCHEN), PlayerStatus.PLAYING).hashCode();
//        final int hashCode2 = new Player(new Zone(KITCHEN), PlayerStatus.PLAYING).hashCode();
//        final int hashCode3 = new Player(null, PlayerStatus.PLAYING).hashCode();
//        final int hashCode4 = new Player(null, PlayerStatus.PLAYING).hashCode();
//        final int hashCode5 = new Player(new Zone(BATHROOM), null).hashCode();
//        final int hashCode6 = new Player(new Zone(BATHROOM), null).hashCode();
//
//        assertThat(hashCode1).isEqualTo(hashCode2);
//        assertThat(hashCode3).isEqualTo(hashCode4);
//        assertThat(hashCode5).isEqualTo(hashCode6);
//
//    }
//
//    @Test
//    public void hashCodeShouldNotBeTheSame() throws Exception {
//        final int hashCode1 = new Player(new Zone(KITCHEN), PlayerStatus.PLAYING).hashCode();
//        final int hashCode2 = new Player(null, PlayerStatus.PLAYING).hashCode();
//        final int hashCode3 = new Player(new Zone(BATHROOM), null).hashCode();
//
//        assertThat(hashCode1).isNotEqualTo(hashCode2);
//        assertThat(hashCode1).isNotEqualTo(hashCode3);
//        assertThat(hashCode2).isNotEqualTo(hashCode3);
//    }
//
//    @Test
//    public void toStringShouldContainZoneNameAndStatus() {
//        final String zoneName = "Library";
//        final Player player = new Player(new Zone(zoneName), PlayerStatus.PLAYING);
//        final String actual = player.toString();
//
//        assertThat(actual).contains(zoneName);
//        assertThat(actual).contains("PLAYING");
//    }
}