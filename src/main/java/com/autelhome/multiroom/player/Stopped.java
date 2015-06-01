package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.AbstractEvent;

import java.util.UUID;

/**
 * Event triggered when player has been asked to stop.
 *
 * @author xdeclercq
 */
public class Stopped extends AbstractEvent {

    /**
     * Constructor.
     *
     * @param zoneId the zone id
     */
    public Stopped(final UUID zoneId) {
        super(zoneId);
    }
}
