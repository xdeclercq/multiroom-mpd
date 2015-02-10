package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.Command;
import com.autelhome.multiroom.zone.Zone;

/**
 * @author xdeclercq
 */
public class AbstractPlayerCommand implements Command {
    protected final Zone zone;

    public AbstractPlayerCommand(final Zone zone) {
        this.zone = zone;
    }

    public Zone getZone() {
        return zone;
    }
}
