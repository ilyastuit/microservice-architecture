package com.ilyastuit.microservices.resourceprocessor.service;

import com.ilyastuit.microservices.resourceprocessor.dto.SongMetaDataDTO;

public interface HttpSongService {

    String SONGS_PATH = "songs";

    void createSong(SongMetaDataDTO songMetaDataDTO);

    void deleteSong(long... ids);
}
