package com.ilyastuit.microservices.songservice.repository;

import com.ilyastuit.microservices.songservice.entity.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends CrudRepository<Song, Long> {
}
