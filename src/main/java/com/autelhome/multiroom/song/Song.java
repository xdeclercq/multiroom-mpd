package com.autelhome.multiroom.song;

import org.bff.javampd.objects.MPDSong;

import java.util.Objects;

/**
 * Represents a song.
 *
 * @author xdeclercq
 */
public class Song {

    private final String title;

    /**
     * Constructor.
     *
     * @param title a title
     */
    public Song(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Returns a new song from an MPD song.
     *
     * @param mpdSong an MPD song
     * @return a new song
     */
    public static Song fromMPDSong(final MPDSong mpdSong) {
        return new Song(mpdSong.getTitle());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Song song = (Song) o;

        return Objects.equals(title, song.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
