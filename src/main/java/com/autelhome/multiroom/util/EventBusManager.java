package com.autelhome.multiroom.util;

import com.autelhome.multiroom.player.PlayCommand;
import com.autelhome.multiroom.player.PlayerCommandHandlers;
import com.autelhome.multiroom.player.StopCommand;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.lifecycle.Managed;

/**
 * Manager to reigster command handlers to event bus.
 *
 * @author xdeclercq
 */
@Singleton
public class EventBusManager implements Managed {

    private final EventBus eventBus;
    private final PlayerCommandHandlers playerCommandHandlers; // NOPMD

    /**
     * Constructor.
     *
     * @param eventBus the event bus
     * @param playerCommandHandlers the player commmand handlers
     */
    @Inject
    public EventBusManager(final EventBus eventBus, final PlayerCommandHandlers playerCommandHandlers ) {
        this.eventBus = eventBus;
        this.playerCommandHandlers = playerCommandHandlers;
    }

    @Override
    public void start() throws Exception {
        eventBus.register(playerCommandHandlers::handlePlay, PlayCommand.class);

        eventBus.register(playerCommandHandlers::handleStop, StopCommand.class);
    }

    @Override
    public void stop() throws Exception {
        // do nothing
    }
}
