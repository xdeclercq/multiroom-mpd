package com.autelhome.multiroom.socket;

/**
 * Encapsulates the entity to be sent via websocket.
 *
 * @author xdeclercq
 */
public class SocketMessage<T> {

    private final T entity;

    /**
     * Constructor.
     *
     * @param entity an entity
     */
    public SocketMessage(final T entity) {
        this.entity = entity;
    }

    public T getEntity() {
        return entity;
    }
}
