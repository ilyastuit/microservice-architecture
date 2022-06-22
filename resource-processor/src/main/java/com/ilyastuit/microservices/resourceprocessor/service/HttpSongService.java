package com.ilyastuit.microservices.resourceprocessor.service;

import com.ilyastuit.microservices.resourceprocessor.dto.SongDTO;

public interface HttpSongService {

    String SONGS_PATH = "songs";

    void createSong(SongDTO songDTO);

    void deleteSong(long... ids);
}
