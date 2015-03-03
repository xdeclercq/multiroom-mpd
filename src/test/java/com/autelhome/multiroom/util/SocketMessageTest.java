package com.autelhome.multiroom.util;

import com.autelhome.multiroom.player.PlayerStatus;
import org.junit.Test;

import java.net.URI;

import static org.fest.assertions.api.Assertions.assertThat;

public class SocketMessageTest {

    @Test
    public void create() throws Exception {
        final PlayerStatus entity = PlayerStatus.PLAYING;
        final URI requestURI = URI.create("/test");
        final SocketMessage testSubject = new SocketMessage(entity, requestURI);
        assertThat(testSubject.getEntity()).isEqualTo(entity);
        assertThat(testSubject.getRequestURI()).isEqualTo(requestURI);
    }

}