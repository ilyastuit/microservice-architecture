package com.ilyastuit.microservices.songservice.builder;

import com.ilyastuit.microservices.songservice.entity.Song;

public class SongBuilder {

    private static final String VALID_NAME = "name";
    private static final String VALID_ARTIST = "artist";
    private static final String VALID_ALBUM = "album";
    private static final long VALID_LENGTH = 10;
    private static final long VALID_RESOURCE_ID = 1;
    private static final int VALID_YEAR = 1990;

    private String name = VALID_NAME;

    private String artist = VALID_ARTIST;

    private String album = VALID_ALBUM;

    private long length = VALID_LENGTH;

    private long resourceId = VALID_RESOURCE_ID;

    private int year = VALID_YEAR;

    public SongBuilder withName(String name) {
        var copy = copy();
        copy.name = name;
        return copy;
    }

    public SongBuilder withArtist(String artist) {
        var copy = copy();
        copy.artist = artist;
        return copy;
    }

    public SongBuilder withAlbum(String album) {
        var copy = copy();
        copy.album = album;
        return copy;
    }

    public SongBuilder withLength(long length) {
        var copy = copy();
        copy.length = length;
        return copy;
    }

    public SongBuilder withResourceId(long resourceId) {
        var copy = copy();
        copy.resourceId = resourceId;
        return copy;
    }

    public SongBuilder withYear(int year) {
        var copy = copy();
        copy.year = year;
        return copy;
    }

    public Song build() {
        return new Song(
                this.name,
                this.artist,
                this.album,
                this.length,
                this.resourceId,
                this.year
        );
    }

    public SongBuilder copy() {
        var copy = new SongBuilder();
        copy.name = this.name;
        copy.artist = this.artist;
        copy.album = this.album;
        copy.length = this.length;
        copy.resourceId = this.resourceId;
        copy.year = this.year;
        return copy;
    }

}
