package com.autelhome.multiroom.util;

import com.google.inject.Inject;
import org.atmosphere.config.service.BroadcasterService;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceFactory;
import org.atmosphere.cpr.DefaultBroadcaster;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Broadcaster to give separate messages to different resources depending on the request URI.
 * This allows to put the links in the response that are tailored for each connected resource.
 *
 * @author xdeclercq
 */
@BroadcasterService
public class SocketBroadcaster extends DefaultBroadcaster {

    private final AtmosphereResourceFactory atmosphereResourceFactory;

    /**
     * Constructor.
     *
     * @param atmosphereResourceFactory an {@link AtmosphereResourceFactory} instance
     */
    @Inject
    public SocketBroadcaster(final AtmosphereResourceFactory atmosphereResourceFactory) {
        this.atmosphereResourceFactory = atmosphereResourceFactory;
    }

    /**
     * Broadcasts an entity to resources.
     *
     * @param entity the entity to broadcast
     */
    public <T> void broadcastEntity(final T entity) {
        final Collection<AtmosphereResource> atmosphereResources = atmosphereResourceFactory.findAll();
        final Map<URI, Set<AtmosphereResource>> atmosphereResourcesByRequest = atmosphereResources
                .stream()
                .collect(Collectors.groupingBy(resource ->  URI.create("ws" + resource.getRequest().getRequestURL().toString().substring(4)), Collectors.toSet()));
        atmosphereResourcesByRequest.forEach( (uri, resources)  -> super.broadcast(new SocketMessage<>(entity, uri), resources));
    }

}
