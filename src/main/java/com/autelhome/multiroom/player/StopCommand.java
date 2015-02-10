package com.autelhome.multiroom.player;

import com.autelhome.multiroom.zone.Zone;

/**
 * Command to stop the player associated to a zone.
 *
 * @author xdeclercq
 */
public class StopCommand extends AbstractPlayerCommand {

    /**
     * Constructor.
     *
     * @param zone the zone on which the command should be executed
     */
    public StopCommand(final Zone zone) {
        super(zone);
    }
}
