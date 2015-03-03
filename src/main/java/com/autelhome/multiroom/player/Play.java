package com.autelhome.multiroom.player;

import com.autelhome.multiroom.util.AbstractCommand;

import java.util.UUID;

/**
 * Command to play/resume the player associated to a zone.
 *
 * @author xdeclercq
 */
public class Play extends AbstractCommand {

    /**
     * Constructor.
     *
     * @param id the player id
     * @param originalVersion the zone version on which this command applies
     */
    public Play(final UUID id, final int originalVersion) {
        super(id, originalVersion);
    }

}
