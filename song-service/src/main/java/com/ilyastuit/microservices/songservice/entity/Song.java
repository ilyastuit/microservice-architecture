package com.ilyastuit.microservices.songservice.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String artist;

    private String album;

    private long length;

    @Column(name = "resource_id")
    private long resourceId;

    private int year;

    protected Song() {}

    public Song(String name, String artist, String album, long length, long resourceId, int year) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.length = length;
        this.resourceId = resourceId;
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public long getLength() {
        return length;
    }

    public long getResourceId() {
        return resourceId;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id == song.id &&
                length == song.length &&
                resourceId == song.resourceId &&
                year == song.year &&
                Objects.equals(name, song.name) &&
                Objects.equals(artist, song.artist) &&
                Objects.equals(album, song.album);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, artist, album, length, resourceId, year);
    }

    @Override
    public String toString() {
        return "Song{" +
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
