package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.util.AbstractCreateCommand;

import java.util.UUID;

/**
 * Command to create a zone.
 *
 * @author xdeclercq
 */
public class CreateZone extends AbstractCreateCommand {

    private final String name;
    private final int mpdInstancePortNumber;


    /**
     * Constructor.
     *
     * @param id the id of the new zone
     * @param name the name of the zone to be created
     * @param mpdInstancePortNumber the port number of the MPD instance related to the zone named {@code zoneName}
     */
    public CreateZone(final UUID id, final String name, final int mpdInstancePortNumber) {
        super(id);
        this.name = name;
        this.mpdInstancePortNumber = mpdInstancePortNumber;
    }

    public int getMpdInstancePortNumber() {
        return mpdInstancePortNumber;
    }

    public String getName() {
        return name;
    }
}
