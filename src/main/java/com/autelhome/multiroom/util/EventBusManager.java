package com.autelhome.multiroom.util;

import com.autelhome.multiroom.player.*;
import com.autelhome.multiroom.zone.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.lifecycle.Managed;

/**
 * Manager to register message handlers to event bus.
 *
 * @author xdeclercq
 */
@Singleton
public class EventBusManager implements Managed {

    private final EventBus eventBus; // NOPMD
    private final ZoneCommandHandlers zoneCommandHandlers; // NOPMD
    private final PlayerCommandHandlers playerCommandHandlers; // NOPMD
    private final ZonesView zonesView; // NOPMD
    private final PlayersView playersView; // NOPMD

    /**
     * Constructor.
     *
     * @param eventBus the event bus
     * @param zoneCommandHandlers the zone command handlers
     * @param playerCommandHandlers the player command handlers
     * @param zonesView the zones view
     * @param playersView the players view
     */
    @Inject
    public EventBusManager(final EventBus eventBus, final ZoneCommandHandlers zoneCommandHandlers,
                           final PlayerCommandHandlers playerCommandHandlers,
                           final ZonesView zonesView,
                           final PlayersView playersView) {
        this.eventBus = eventBus;
        this.zoneCommandHandlers = zoneCommandHandlers;
        this.playerCommandHandlers = playerCommandHandlers;
        this.zonesView = zonesView;
        this.playersView = playersView;
    }

    @Override
    public void start() throws Exception {
        eventBus.register(zoneCommandHandlers::handleCreate, CreateZone.class);
        eventBus.register(playerCommandHandlers::handlePlay, Play.class);
        eventBus.register(playerCommandHandlers::handleStop, Stop.class);
        eventBus.register(playerCommandHandlers::handlePause, Pause.class);
        eventBus.register(playerCommandHandlers::handleChangePlayerStatus, ChangePlayerStatus.class);


        eventBus.register(zonesView::handleCreated, ZoneCreated.class);
        eventBus.register(zonesView::handleDefault, Played.class);
        eventBus.register(zonesView::handleDefault, Paused.class);
        eventBus.register(zonesView::handleDefault, Stopped.class);
        eventBus.register(zonesView::handleDefault, PlayerStatusUpdated.class);
        eventBus.register(playersView::handleCreated, ZoneCreated.class);
        eventBus.register(playersView::handlePlayed, Played.class);
        eventBus.register(playersView::handlePaused, Paused.class);
        eventBus.register(playersView::handleStopped, Stopped.class);
        eventBus.register(playersView::handlePlayerStatusUpdated, PlayerStatusUpdated.class);
    }

    @Override
    public void stop() throws Exception {
        // do nothing
    }
}