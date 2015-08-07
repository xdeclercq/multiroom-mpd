package com.autelhome.multiroom.player;

import com.autelhome.multiroom.song.Song;
import com.google.common.base.MoreObjects;
import org.bff.javampd.objects.MPDSong;

import java.util.Objects;

/**
 * Represents the current song being played in zone player.
 *
 * @author xdeclercq
 */
public class CurrentSong {

    private final Song song;
    private final int position;

    /**
     * @param song the song
     * @param position the song position
     */
    public CurrentSong(final Song song, final int position) {
        this.song = song;
        this.position = position;
    }

    /**
     * Returns a new current song from an MPD song.
     *
     * @param mpdSong an MPD song
     * @return a new song
     */
    public static CurrentSong fromMPDSong(final MPDSong mpdSong) {
        return new CurrentSong(Song.fromMPDSong(mpdSong), mpdSong.getPosition());
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

        final CurrentSong that = (CurrentSong) o;

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
