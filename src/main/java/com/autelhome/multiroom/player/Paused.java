package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.AbstractEvent;

import java.util.UUID;

/**
 * Event triggered when player has been asked to pause.
 *
 * @author xdeclercq
 */
public class Paused extends AbstractEvent {

    /**
     * Constructor.
     *
     * @param zoneId the zone id
     */
    public Paused(final UUID zoneId) {
        super(zoneId);
    }
}
