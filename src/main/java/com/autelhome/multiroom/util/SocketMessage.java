package com.autelhome.multiroom.util;

import java.net.URI;

/**
 * Encapsulates the entity with the related request URI.
 *
 * @author xdeclercq
 */
public class SocketMessage<T> {

    private final T entity;
    private final URI requestURI;

    /**
     * Constructor.
     *
     * @param entity an entity
     * @param requestURI the request URI
     */
    public SocketMessage(final T entity, final URI requestURI) {
        this.entity = entity;
        this.requestURI = requestURI;
    }

    public T getEntity() {
        return entity;
    }

    public URI getRequestURI() {
        return requestURI;
    }
}
