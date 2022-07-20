package com.ilyastuit.microservice.resourceservice.service.builder;

import com.ilyastuit.microservice.resourceservice.entity.Song;

public class SongBuilder {

    public static final String VALID_NAME = "name";

    private String name = VALID_NAME;


    public SongBuilder withName(String name) {
        var copy = copy();
        copy.name = name;
        return copy;
    }

    public Song build() {
        return new Song(
                this.name
        );
    }

    public SongBuilder copy() {
        var copy = new SongBuilder();
        copy.name = this.name;
        return copy;
    }
}
