package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.mpd.MPDException;
import com.google.common.base.MoreObjects;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.bff.javampd.Playlist;
import org.bff.javampd.exception.MPDPlaylistException;
import org.bff.javampd.objects.MPDSong;

/**
 * Represents a zone playlist.
 *
 * @author xdeclercq
 */
public class ZonePlaylist {

    private final List<PlaylistSong> playlistSongs;

    /**
     * Constructor.
     */
    public ZonePlaylist() {
        playlistSongs = new ArrayList<>();
    }

    /**
     * Constructor.
     *
     * @param playlistSongs a collection of playlist songs
     */
    public ZonePlaylist(final List<PlaylistSong> playlistSongs) {
        this.playlistSongs = playlistSongs;
    }

    public Collection<PlaylistSong> getPlaylistSongs() {
        return playlistSongs;
    }

    /**
     * Returns the song by its posistion in the playlist.
     *
     * @param position a one-based position in the playlist
     * @return the {@link PlaylistSong} at {@code position} in the playlist
     */
    public PlaylistSong getSongAtPosition(final int position) {
        return playlistSongs.get(position - 1);
    }

    /**
     * Builds a {@link ZonePlaylist} instance from a {@link Playlist} instance.
     *
     * @param mpdPlaylist a {@link Playlist} instance
     * @return a new {@link ZonePlaylist} instance
     */
    public static ZonePlaylist fromMPDPlaylist(final Playlist mpdPlaylist) {
        try {
            final List<MPDSong> mpdSongs = mpdPlaylist.getSongList();
            final List<PlaylistSong> playlistSongs = mpdSongs.stream().map(mpdSong -> PlaylistSong.fromMPDSong(mpdSong)).collect(Collectors.toList());
            return new ZonePlaylist(playlistSongs);
        } catch (MPDPlaylistException e) {
            throw new MPDException("Unable to fetch list of songs of MPD playlist", e);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ZonePlaylist that = (ZonePlaylist) o;

        return Objects.equals(playlistSongs, that.playlistSongs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistSongs);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("playlistSongs", playlistSongs)
                .toString();
    }
}
