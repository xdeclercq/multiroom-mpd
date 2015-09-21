package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.AbstractEvent;
import java.util.UUID;

/**
 * Event triggered when player has been asked to play.
 *
 * @author xdeclercq
 */
public class Played extends AbstractEvent {

    /**
     * Constructor.
     *
     * @param zoneId the zone id
     */
    public Played(final UUID zoneId) {
        super(zoneId);
    }

}
