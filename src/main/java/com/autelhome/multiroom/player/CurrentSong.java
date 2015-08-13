package com.autelhome.multiroom.player;

import com.autelhome.multiroom.playlist.PlaylistSong;
import com.autelhome.multiroom.song.Song;
import com.google.common.base.MoreObjects;
import org.bff.javampd.objects.MPDSong;

/**
 * Represents the current song being played in zone player.
 *
 * @author xdeclercq
 */
public class CurrentSong extends PlaylistSong {

    /**
     * @param song the song
     * @param position the song position
     */
    public CurrentSong(final Song song, final int position) {
        super(song, position);
    }

    /**
     * Returns a new current song from an MPD song.
     *
     * @param mpdSong an MPD song
     * @return a new current song
     */
    public static CurrentSong fromMPDSong(final MPDSong mpdSong) {
        return new CurrentSong(Song.fromMPDSong(mpdSong), mpdSong.getPosition() + 1);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("song", getSong())
                .add("position", getPosition())
                .toString();
    }
}
