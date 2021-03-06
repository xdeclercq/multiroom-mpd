package com.autelhome.multiroom.util;

import com.autelhome.multiroom.player.ChangeCurrentSong;
import com.autelhome.multiroom.player.CurrentSongUpdated;
import com.autelhome.multiroom.player.Pause;
import com.autelhome.multiroom.player.Paused;
import com.autelhome.multiroom.player.Play;
import com.autelhome.multiroom.player.PlaySongAtPosition;
import com.autelhome.multiroom.player.Played;
import com.autelhome.multiroom.player.PlayerCommandHandlers;
import com.autelhome.multiroom.player.PlayersView;
import com.autelhome.multiroom.player.SongAtPositionPlayed;
import com.autelhome.multiroom.player.Stop;
import com.autelhome.multiroom.player.Stopped;
import com.autelhome.multiroom.playlist.ChangeZonePlaylist;
import com.autelhome.multiroom.playlist.ZonePlaylistCommandHandlers;
import com.autelhome.multiroom.playlist.ZonePlaylistUpdated;
import com.autelhome.multiroom.playlist.ZonePlaylistsView;
import com.autelhome.multiroom.zone.CreateZone;
import com.autelhome.multiroom.zone.ZoneCommandHandlers;
import com.autelhome.multiroom.zone.ZoneCreated;
import com.autelhome.multiroom.zone.ZonesView;
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
    private final ZonePlaylistCommandHandlers zonePlaylistCommandHandlers; // NOPMD
    private final ZonesView zonesView; // NOPMD
    private final PlayersView playersView; // NOPMD
    private final ZonePlaylistsView zonePlaylistsView; // NOPMD

    /**
     * Constructor.
     *
     * @param eventBus the event bus
     * @param zoneCommandHandlers the zone command handlers
     * @param playerCommandHandlers the player command handlers
     * @param zonePlaylistCommandHandlers the zone playlist command handlers
     * @param zonesView the zones view
     * @param playersView the players view
     * @param zonePlaylistsView the zone playlists view
     */
    @Inject
    public EventBusManager(final EventBus eventBus, final ZoneCommandHandlers zoneCommandHandlers,
                           final PlayerCommandHandlers playerCommandHandlers,
                           final ZonePlaylistCommandHandlers zonePlaylistCommandHandlers,
                           final ZonesView zonesView,
                           final PlayersView playersView,
                           final ZonePlaylistsView zonePlaylistsView) {
        this.eventBus = eventBus;
        this.zoneCommandHandlers = zoneCommandHandlers;
        this.playerCommandHandlers = playerCommandHandlers;
        this.zonePlaylistCommandHandlers = zonePlaylistCommandHandlers;
        this.zonesView = zonesView;
        this.playersView = playersView;
        this.zonePlaylistsView = zonePlaylistsView;
    }

    @Override
    public void start() throws Exception {
        eventBus.register(zoneCommandHandlers::handleCreate, CreateZone.class);
        eventBus.register(playerCommandHandlers::handlePlay, Play.class);
        eventBus.register(playerCommandHandlers::handleStop, Stop.class);
        eventBus.register(playerCommandHandlers::handlePause, Pause.class);
        eventBus.register(playerCommandHandlers::handleChangeCurrentSong, ChangeCurrentSong.class);
        eventBus.register(playerCommandHandlers::handlePlaySong, PlaySongAtPosition.class);
        eventBus.register(zonePlaylistCommandHandlers::handleChangeZonePlaylist, ChangeZonePlaylist.class);


        eventBus.register(zonesView::handleCreated, ZoneCreated.class);
        eventBus.register(zonesView::handleDefault, Played.class);
        eventBus.register(zonesView::handleDefault, Paused.class);
        eventBus.register(zonesView::handleDefault, Stopped.class);
        eventBus.register(zonesView::handleDefault, CurrentSongUpdated.class);
        eventBus.register(zonesView::handleDefault, SongAtPositionPlayed.class);
        eventBus.register(zonesView::handleDefault, ZonePlaylistUpdated.class);
        eventBus.register(playersView::handleCreated, ZoneCreated.class);
        eventBus.register(playersView::handlePlayed, Played.class);
        eventBus.register(playersView::handlePaused, Paused.class);
        eventBus.register(playersView::handleStopped, Stopped.class);
        eventBus.register(playersView::handleCurrentSongUpdated, CurrentSongUpdated.class);
        eventBus.register(playersView::handleSongPlayed, SongAtPositionPlayed.class);
        eventBus.register(zonePlaylistsView::handleCreated, ZoneCreated.class);
        eventBus.register(zonePlaylistsView::handleZonePlaylistUpdated, ZonePlaylistUpdated.class);
    }

    @Override
    public void stop() throws Exception {
        // do nothing
    }
}
