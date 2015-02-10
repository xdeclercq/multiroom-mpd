package com.autelhome.multiroom.player;

import com.autelhome.multiroom.zone.Zone;

/**
 * Command to play/resume the player associated to a zone.
 *
 * @author xdeclercq
 */
public class PlayCommand extends AbstractPlayerCommand {

    /**
     * Constructor.
     *
     * @param zone the zone on which the command should be executed
     */
    public PlayCommand(final Zone zone) {
        super(zone);
    }

}
