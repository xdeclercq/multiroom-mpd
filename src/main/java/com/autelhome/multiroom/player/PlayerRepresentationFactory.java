package com.autelhome.multiroom.player;

import com.autelhome.mpd.app.MultiroomMPDConfiguration;
import com.autelhome.mpd.song.Song;
import com.autelhome.mpd.song.SongRepresentationFactory;
import com.autelhome.mpd.zone.ZoneResource;
import com.google.inject.Inject;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * {@link com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory} for a {@link Player}.
 *
 * @author xavier
 */
public class PlayerRepresentationFactory extends StandardRepresentationFactory
{

    private final MultiroomMPDConfiguration configuration;
    private final SongRepresentationFactory songRepresentationFactory;

    /**
     * Constructor.
     *
     * @param configuration the application configuration
     * @param songRepresentationFactory a {@link SongRepresentationFactory} instance
     */
    @Inject
    public PlayerRepresentationFactory(MultiroomMPDConfiguration configuration, SongRepresentationFactory songRepresentationFactory)
    {
        this.configuration = configuration;
        this.songRepresentationFactory = songRepresentationFactory;
        withFlag(COALESCE_ARRAYS);
    }

    /**
     * Returns a new {@link com.theoryinpractise.halbuilder.api.Representation} of a player.
     *
     * @param player a player
     * @return a new {@link com.theoryinpractise.halbuilder.api.Representation} of the player
     */
    public Representation newRepresentation(Player player)
    {
        URI baseURI = configuration.getBaseURI();
        URI self = UriBuilder
                .fromUri(baseURI)
                .path(ZoneResource.class)
                .path(ZoneResource.class, "getPlayer")
                .build(player.getZoneName());

        URI play =  UriBuilder
                .fromUri(baseURI)
                .path(ZoneResource.class)
                .path(ZoneResource.class, "getPlayer")
                .path(PlayerResource.class, "play")
                .build(player.getZoneName());

        URI pause =  UriBuilder
                .fromUri(baseURI)
                .path(ZoneResource.class)
                .path(ZoneResource.class, "getPlayer")
                .path(PlayerResource.class, "pause")
                .build(player.getZoneName());

        URI stop =  UriBuilder
                .fromUri(baseURI)
                .path(ZoneResource.class)
                .path(ZoneResource.class, "getPlayer")
                .path(PlayerResource.class, "stop")
                .build(player.getZoneName());

        URI prev =  UriBuilder
                .fromUri(baseURI)
                .path(ZoneResource.class)
                .path(ZoneResource.class, "getPlayer")
                .path(PlayerResource.class, "prev")
                .build(player.getZoneName());

        URI next =  UriBuilder
                .fromUri(baseURI)
                .path(ZoneResource.class)
                .path(ZoneResource.class, "getPlayer")
                .path(PlayerResource.class, "next")
                .build(player.getZoneName());

        URI volume =  UriBuilder
                .fromUri(baseURI)
                .path(ZoneResource.class)
                .path(ZoneResource.class, "getPlayer")
                .path(PlayerResource.class, "volume")

                .build(player.getZoneName());

        Song currentSong = player.getCurrentSong();
        Representation representation = newRepresentation(self)
                .withProperty("status", player.getStatus())
                .withProperty("volume", player.getVolume().getValue())
                .withLink("play", play.toString().concat("{?position}"))
                .withLink("pause", pause)
                .withLink("stop", stop)
                .withLink("prev", prev)
                .withLink("next", next)
                .withLink("volume", volume.toString().concat("?volume={volume}"));

        if (currentSong!=null)
        {
            representation.withRepresentation("currentsong", songRepresentationFactory.newRepresentation(currentSong, player.getZoneName(), currentSong.getPosition()));
        }

        return representation;
    }

}
