package com.autelhome.multiroom.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xdeclercq
 */
public class PlayerCommandHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerCommandHandlers.class);

    public void handle(PlayCommand playCommand) {
        LOGGER.info("PLAY: YOUPIE");
    }
}
