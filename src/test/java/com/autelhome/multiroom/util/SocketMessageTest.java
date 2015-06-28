package com.autelhome.multiroom.util;

import com.autelhome.multiroom.player.PlayerStatus;
import com.autelhome.multiroom.socket.SocketMessage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class SocketMessageTest {

    @Test
    public void create() throws Exception {
        final PlayerStatus entity = PlayerStatus.PLAYING;
        final SocketMessage testSubject = new SocketMessage(entity);
        assertThat(testSubject.getEntity()).isEqualTo(entity);
    }

}