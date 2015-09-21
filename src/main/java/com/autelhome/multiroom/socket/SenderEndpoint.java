package com.autelhome.multiroom.socket;

/**
 * Broadcasting Socket Endpoint.
 *
 * @author xdeclercq
 *
 * @param <T> the type of entities to be sent to the remote client.
 */
public interface SenderEndpoint<T> {

    /**
     * Sends an entity to the remote client.
     *
     * @param entity the entity to be sent
     */
    void sendEntity(T entity);
}
