package com.ilyastuit.microservices.resourceprocessor.builder;

import com.ilyastuit.microservices.resourceprocessor.dto.SongMetaDataDTO;

public class SongMetaDataDTOBuilder {

    public static final long VALID_SONG_DTO_ID = 1;
    public static final String VALID_NAME = "Song name";
    public static final String VALID_ARTIST = "Artist";
    public static final String VALID_ALBUM = "Album";
    public static final long VALID_LENGTH = 10;
    public static final long VALID_RESOURCE_ID = 1;
    public static final int VALID_YEAR = 1990;

    private long id = VALID_SONG_DTO_ID;
    private String name = VALID_NAME;
    private String artist = VALID_ARTIST;
    private String album = VALID_ALBUM;
    private long length = VALID_LENGTH;
    private long resourceId = VALID_RESOURCE_ID;
    private int year = VALID_YEAR;

    public SongMetaDataDTOBuilder withId(long id) {
        var copy = copy();
        copy.id = id;
        return copy;
    }

    public SongMetaDataDTOBuilder withName(String name) {
        var copy = copy();
        copy.name = name;
        return copy;
    }

    public SongMetaDataDTOBuilder withArtist(String artist) {
        var copy = copy();
        copy.artist = artist;
        return copy;
    }

    public SongMetaDataDTOBuilder withAlbum(String album) {
        var copy = copy();
        copy.album = album;
        return copy;
    }

    public SongMetaDataDTOBuilder withLength(long length) {
        var copy = copy();
        copy.length = length;
        return copy;
    }

    public SongMetaDataDTOBuilder withResourceId(long resourceId) {
        var copy = copy();
        copy.resourceId = resourceId;
        return copy;
    }

    public SongMetaDataDTOBuilder withYear(int year) {
        var copy = copy();
        copy.year = year;
        return copy;
    }

    public SongMetaDataDTO build() {
        var songMetaDataDTO = new SongMetaDataDTO();
        songMetaDataDTO.setId(this.id);
        songMetaDataDTO.setName(this.name);
        songMetaDataDTO.setArtist(this.artist);
        songMetaDataDTO.setAlbum(this.album);
        songMetaDataDTO.setLength(this.length);
        songMetaDataDTO.setResourceId(this.resourceId);
        songMetaDataDTO.setYear(this.year);
        return songMetaDataDTO;
    }

    private SongMetaDataDTOBuilder copy() {
        var other = new SongMetaDataDTOBuilder();
        other.id = this.id;
        other.name = this.name;
        other.artist = this.artist;
        other.album = this.album;
        other.length = this.length;
        other.resourceId = this.resourceId;
        other.year = this.year;
        return other;
    }
}
