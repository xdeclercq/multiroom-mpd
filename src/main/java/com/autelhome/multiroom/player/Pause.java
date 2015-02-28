package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.AbstractCommand;

import java.util.UUID;

/**
 * Command to pause the player associated to a zone.
 *
 * @author xdeclercq
 */
public class Pause extends AbstractCommand {

    /**
     * Constructor.
     *
     * @param id the player id
     * @param originalVersion the zone version on which this command applies
     */
    public Pause(final UUID id, final int originalVersion) {
        super(id, originalVersion);
    }

}
