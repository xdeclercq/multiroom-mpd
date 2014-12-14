package com.autelhome.multiroom.util;

import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HalJsonMessageBodyWriterTest {

    private HalJsonMessageBodyWriter testSubject = new HalJsonMessageBodyWriter();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void isWriteableShouldReturnTrue() throws Exception {
        boolean actual = testSubject.isWriteable(ReadableRepresentation.class, null, null, new MediaType("application", "hal+json"));
        assertThat(actual).isTrue();
    }

    @Test
    public void isWriteableWithWrongMediaTypeShouldReturnFalse() throws Exception {
        boolean actual = testSubject.isWriteable(ReadableRepresentation.class, null, null, new MediaType("application", "json"));
        assertThat(actual).isFalse();
    }

    @Test
    public void isWriteableWithWrongClassShouldReturnFalse() throws Exception {
        boolean actual = testSubject.isWriteable(String.class, null, null, new MediaType("application", "hal+json"));
        assertThat(actual).isFalse();
    }

    @Test
    public void getSize() throws Exception {
        StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();
        Representation representation = representationFactory.newRepresentation(URI.create("/test"));
        long expected = representation.toString(RepresentationFactory.HAL_JSON).length();

        long actual = testSubject.getSize(representation, null, null, null, new MediaType("application", "hal+json"));
        
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void writeTo() throws Exception {
        Representation representation = mock(Representation.class);

        final OutputStream outputStream = mock(OutputStream.class);

        testSubject.writeTo(representation, null, null, null, new MediaType("application", "hal+json"), null, outputStream);

        verify(representation).toString(anyString(), any(OutputStreamWriter.class));
    }
}