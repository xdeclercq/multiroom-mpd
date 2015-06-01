package com.autelhome.multiroom.hal;

import com.google.common.base.Charsets;
import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * JAX-RS support for application/hal+json media type.
 *
 * @author xdeclercq
 */
@Provider
@Produces({RepresentationFactory.HAL_JSON})
public class HalJsonMessageBodyWriter implements MessageBodyWriter<ReadableRepresentation> {

    private static final MediaType HAL_JSON_TYPE = new MediaType("application", "hal+json");

    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return ReadableRepresentation.class.isAssignableFrom(type) && mediaType.isCompatible(HAL_JSON_TYPE);
    }

    @Override
    public long getSize(final ReadableRepresentation representation, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return representation.toString(mediaType.toString()).length();
    }

    @Override
    public void writeTo(final ReadableRepresentation representation, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream) throws IOException, WebApplicationException {
        representation.toString(mediaType.toString(), new OutputStreamWriter(entityStream, Charsets.UTF_8));
    }
}
