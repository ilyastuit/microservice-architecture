package com.ilyastuit.microservices.songservice.service;

import com.ilyastuit.microservices.songservice.entity.Song;
import com.ilyastuit.microservices.songservice.http.dto.SongDTO;

public interface SongService {
    Song save(SongDTO songDTO);

    Song getById(long id);

    void deleteAll(Long[] ids);
}