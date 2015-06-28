package com.autelhome.multiroom.socket;

/**
 *
 * Broadcasting Socket Endpoint.
 *
 * @author xdeclercq
 */
public interface SenderEndpoint<T> {

    /**
     * Sends an entity to the remote client.
     *
     * @param entity the entity to be sent
     */
    void sendEntity(T entity);
}
