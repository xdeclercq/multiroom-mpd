package com.autelhome.multiroom.socket;

import com.google.inject.Inject;

import java.util.List;

/**
 * Broadcaster to send messages to web socket sessions.
 *
 * @author xdeclercq
 */
public class SocketBroadcaster {

    private final EndpointRegistry endpointRegistry;

    /**
     * Constructor.
     *
     * @param endpointRegistry the endpoint registry
     */
    @Inject
    public SocketBroadcaster(final EndpointRegistry endpointRegistry) {
        this.endpointRegistry = endpointRegistry;
    }

    /**
     * Broadcast a message to all opened sessions registered for a given key.
     *
     * @param key a key
     * @param entity the entity to broadcast
     */
    public <T> void broadcast(final String key, final T entity) {
        final List<Object> endpoints = endpointRegistry.getEndpoints(key);

        endpoints.forEach(endpoint -> {
            if (endpoint instanceof SenderEndpoint) {
                ((SenderEndpoint) endpoint).sendEntity(entity);
            }
        });
    }
}
