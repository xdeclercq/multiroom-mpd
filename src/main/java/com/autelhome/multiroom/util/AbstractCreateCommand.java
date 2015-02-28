package com.autelhome.multiroom.util;

import java.util.UUID;

/**
 * Abstract command.
 *
 * @author xdeclercq
 */
public abstract class AbstractCreateCommand extends AbstractCommand {

    /**
     * Constructor to create new aggregate (put the original version to -1).
     *
     * @param aggregateId the aggregate id on which this command applies
     */
    protected AbstractCreateCommand(final UUID aggregateId) {
        super(aggregateId, -1);
    }

}
