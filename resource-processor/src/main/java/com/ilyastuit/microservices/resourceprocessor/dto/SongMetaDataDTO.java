package com.ilyastuit.microservices.resourceprocessor.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

public class SongMetaDataDTO {

    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String artist;

    @NotBlank
    private String album;

    @PositiveOrZero
    private long length;

    @Positive
    private long resourceId;

    @Positive
    private int year;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongMetaDataDTO songMetaDataDTO = (SongMetaDataDTO) o;
        return id == songMetaDataDTO.id &&
                length == songMetaDataDTO.length &&
                resourceId == songMetaDataDTO.resourceId &&
                year == songMetaDataDTO.year &&
                Objects.equals(name, songMetaDataDTO.name) &&
                Objects.equals(artist, songMetaDataDTO.artist) &&
                Objects.equals(album, songMetaDataDTO.album);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, artist, album, length, resourceId, year);
    }

    @Override
    public String toString() {
        return "SongDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", length=" + length +
                ", resourceId=" + resourceId +
                ", year=" + year +
                '}';
    }
}
