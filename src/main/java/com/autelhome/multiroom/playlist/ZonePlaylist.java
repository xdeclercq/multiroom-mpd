package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.mpd.MPDException;
import com.autelhome.multiroom.song.Song;
import com.google.common.base.MoreObjects;
import org.bff.javampd.Playlist;
import org.bff.javampd.exception.MPDPlaylistException;
import org.bff.javampd.objects.MPDSong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a zone playlist
 *
 * @author xdeclercq
 */
public class ZonePlaylist {

    private final List<Song> songs;

    /**
     * Constructor.
     */
    public ZonePlaylist() {
        songs = new ArrayList<>();
    }

    /**
     * Constructor.
     *
     * @param songs a collection of songs
     */
    public ZonePlaylist(final List<Song> songs) {
        this.songs = songs;
    }

    public Collection<Song> getSongs() {
        return songs;
    }

    public Song getSongAtPosition(final int position) {
        return songs.get(position);
    }

    public static ZonePlaylist fromMPDPlaylist(final Playlist mpdPlaylist) {
        try {
            final List<MPDSong> mpdSongs = mpdPlaylist.getSongList();
            final List<Song> songs = mpdSongs.stream().map( mpdSong -> Song.fromMPDSong(mpdSong)).collect(Collectors.toList());
            return new ZonePlaylist(songs);

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

        return Objects.equals(songs, that.songs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songs);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("songs", songs)
                .toString();
    }
}
