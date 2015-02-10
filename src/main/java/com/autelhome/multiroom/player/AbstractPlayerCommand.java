package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.Command;
import com.autelhome.multiroom.zone.Zone;

/**
 * Abstract player command.
 *
 * @author xdeclercq
 */
public abstract class AbstractPlayerCommand implements Command {

    protected final Zone zone;

    /**
     * Constructor.
     *
     * @param zone the zone on which the command should be executed
     */
    protected AbstractPlayerCommand(final Zone zone) {
        this.zone = zone;
    }

    public Zone getZone() {
        return zone;
    }
}
