package com.autelhome.multiroom.playlist;

import com.autelhome.multiroom.song.Song;
import com.google.common.base.MoreObjects;
import java.util.Objects;
import org.bff.javampd.objects.MPDSong;

/**
 * Represents a song in a playlist.
 *
 * @author xdeclercq
 */
public class PlaylistSong {

    private final Song song;
    private final int position;

    /**
     * @param song the song
     * @param position the song position
     */
    public PlaylistSong(final Song song, final int position) {
        this.song = song;
        this.position = position;
    }

    /**
     * Returns a new playlist song from an MPD song.
     *
     * @param mpdSong an MPD song
     * @return a new playlist song
     */
    public static PlaylistSong fromMPDSong(final MPDSong mpdSong) {
        return new PlaylistSong(Song.fromMPDSong(mpdSong), mpdSong.getPosition() + 1);
    }

    public Song getSong() {
        return song;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final PlaylistSong that = (PlaylistSong) o;

        if (position != that.position) {
            return false;
        }
        return Objects.equals(song, that.song);
    }

    @Override
    public int hashCode() {
        return Objects.hash(song, position);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("song", song)
                .add("position", position)
                .toString();
    }
}
