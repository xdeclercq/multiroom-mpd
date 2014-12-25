package com.autelhome.multiroom.zone;

import com.autelhome.multiroom.hal.BaseRepresentationFactory;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

/**
 * {@link StandardRepresentationFactory} for a {@link Zone}.
 *
 * @author xavier
 */
public class ZoneRepresentationFactory extends BaseRepresentationFactory
{

    /**
     * Constructor.
     */
    @Inject
    public ZoneRepresentationFactory()
    {
        super();
    }

    /**
     * Returns a new {@link Representation} of a zone.
     *
     * @param zone a zone
     * @return a new {@link Representation} of the zone
     */
    public Representation newRepresentation(final Zone zone)
    {
        return newRepresentation()
                .withProperty("name", zone.getName());
    }

}
