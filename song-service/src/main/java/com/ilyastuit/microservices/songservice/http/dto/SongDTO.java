package com.ilyastuit.microservices.songservice.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

public class SongDTO {

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
    @JsonProperty("resource_id")
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
        SongDTO songDTO = (SongDTO) o;
        return id == songDTO.id &&
                length == songDTO.length &&
                resourceId == songDTO.resourceId &&
                year == songDTO.year &&
                Objects.equals(name, songDTO.name) &&
                Objects.equals(artist, songDTO.artist) &&
                Objects.equals(album, songDTO.album);
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
