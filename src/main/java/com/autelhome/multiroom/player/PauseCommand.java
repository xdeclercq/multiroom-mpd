package com.autelhome.multiroom.player;

import com.autelhome.multiroom.zone.Zone;

/**
 * Command to pause the player associated to a zone.
 *
 * @author xdeclercq
 */
public class PauseCommand extends AbstractPlayerCommand {

    /**
     * Constructor.
     *
     * @param zone the zone on which the command should be executed
     */
    public PauseCommand(final Zone zone) {
        super(zone);
    }

}
