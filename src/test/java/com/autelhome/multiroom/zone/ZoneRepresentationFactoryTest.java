package com.autelhome.multiroom.zone;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ZoneRepresentationFactoryTest {

    private final ZoneRepresentationFactory testSubject = new ZoneRepresentationFactory();

    @Test
    public void newRepresentation() throws Exception {
        final StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();
        final String expected = representationFactory
                .newRepresentation()
                .withProperty("name", "myZone")
                .toString(RepresentationFactory.HAL_JSON);


        final Zone zone = new Zone("myZone");
        final String actual = testSubject.newRepresentation(zone).toString(RepresentationFactory.HAL_JSON);

        assertThat(actual).isEqualTo(expected);
    }
}