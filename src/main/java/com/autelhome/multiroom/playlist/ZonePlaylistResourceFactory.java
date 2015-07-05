package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.zone.ZoneDto;
import com.google.inject.Inject;

/**
 * Factory of a {@link ZonePlaylistResource}.
 *
 * @author xdeclercq
 */
public class ZonePlaylistResourceFactory
{

    private final ZonePlaylistService zonePlaylistService;
    private final ZonePlaylistRepresentationFactory zonePlaylistRepresentationFactory;

    /**
     * Constructor.
     *
     * @param zonePlaylistService a {@link ZonePlaylistService} instance
     * @param zonePlaylistRepresentationFactory a {@link ZonePlaylistRepresentationFactory} instance
     */
    @Inject
    public ZonePlaylistResourceFactory(final ZonePlaylistService zonePlaylistService, final ZonePlaylistRepresentationFactory zonePlaylistRepresentationFactory)
    {
        this.zonePlaylistService = zonePlaylistService;
        this.zonePlaylistRepresentationFactory = zonePlaylistRepresentationFactory;
    }

    /**
     * Returns a new {@link ZonePlaylistResource} instance for a zone.
     *
     * @param zoneDto the zone
     * @return a new {@link ZonePlaylistResource} instance for the playliwt related to the provided {@code zoneDto}
     */
    public ZonePlaylistResource newInstance(final ZoneDto zoneDto)
    {
        return new ZonePlaylistResource(zoneDto, zonePlaylistService, zonePlaylistRepresentationFactory);
    }
}
