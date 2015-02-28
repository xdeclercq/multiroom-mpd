package com.autelhome.multiroom.util;

import java.util.UUID;

/**
 * An event.
 *
 * @author xdeclercq
 */
public interface Event extends Message {


    /**
     * Returns the version.
     *
     * @return the version
     */
    int getVersion();

    /**
     * Sets the version.
     *
     * @param version the version
     */
    void setVersion(final int version);

    /**
     * Returns the aggregate id.
     *
     * @return the aggregate id
     */
    UUID getId();
}
