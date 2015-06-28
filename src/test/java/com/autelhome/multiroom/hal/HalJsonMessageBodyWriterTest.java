package com.autelhome.multiroom.hal;

import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HalJsonMessageBodyWriterTest {

    private static final MediaType MEDIA_TYPE_HAL_JSON = new MediaType("application", "hal+json");
    private final HalJsonMessageBodyWriter testSubject = new HalJsonMessageBodyWriter();

    @Test
    public void isWriteableShouldReturnTrue() throws Exception {
        final boolean actual = testSubject.isWriteable(ReadableRepresentation.class, null, null, MEDIA_TYPE_HAL_JSON);
        assertThat(actual).isTrue();
    }

    @Test
    public void isWriteableWithWrongMediaTypeShouldReturnFalse() throws Exception {
        final boolean actual = testSubject.isWriteable(ReadableRepresentation.class, null, null, new MediaType("application", "json"));
        assertThat(actual).isFalse();
    }

    @Test
    public void isWriteableWithWrongClassShouldReturnFalse() throws Exception {
        final boolean actual = testSubject.isWriteable(String.class, null, null, MEDIA_TYPE_HAL_JSON);
        assertThat(actual).isFalse();
    }

    @Test
    public void getSize() throws Exception {
        final StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();
        final Representation representation = representationFactory.newRepresentation(URI.create("/test"));
        final long expected = representation.toString(RepresentationFactory.HAL_JSON).length();

        final long actual = testSubject.getSize(representation, null, null, null, MEDIA_TYPE_HAL_JSON);
        
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void writeTo() throws Exception {
        final Representation representation = mock(Representation.class);

        final OutputStream outputStream = mock(OutputStream.class);

        testSubject.writeTo(representation, null, null, null, MEDIA_TYPE_HAL_JSON, null, outputStream);

        verify(representation).toString(anyString(), any(OutputStreamWriter.class));
    }
}