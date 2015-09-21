package com.autelhome.multiroom.socket;

import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registry of websocket endpoints.
 *
 * @author xdeclercq
 */
@Singleton
public class EndpointRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointRegistry.class);

    private final Map<String, List<Object>> endpoints = Collections.synchronizedMap(new HashMap<>());
    private final Map<Object, String> keys = Collections.synchronizedMap(new HashMap<>());


    /**
     * Register an endpoint.
     *
     * @param key a key
     * @param endpoint the endpoint to register
     */
    public void register(final String key, final Object endpoint) {
        final List<Object> endpointsForKey = endpoints.getOrDefault(key, new ArrayList<>());
        endpointsForKey.add(endpoint);
        endpoints.put(key, endpointsForKey);
        keys.put(endpoint, key);

    }

    /**
     * Unregister an endpoint.
     *
     * @param endpoint the endpoint to unregister
     */
    public void unregister(final Object endpoint) {
        final String key = keys.remove(endpoint);
        if (key == null) {
            throw new IllegalArgumentException("Unable to unregister endpoint as it is not registered");
        }
        final List<Object> endpointsForKey = endpoints.getOrDefault(key, new ArrayList<>());
        endpointsForKey.remove(endpoint);
        LOGGER.info("endpoint (type={}) unregistered - {} endpoint(s) remaining", endpoint.getClass(), endpoints.size());
    }

    /**
     * Retrieves the endpoints associated to a key.
     *
     * @param key a key
     * @return the endpoints associated to the key
     */
    public List<Object> getEndpoints(final String key) {
        return endpoints.getOrDefault(key, new ArrayList<>());
    }

}
