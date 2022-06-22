package com.ilyastuit.microservice.resourceservice.repository;

import com.ilyastuit.microservice.resourceservice.entity.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends CrudRepository<Song, Long> {

    Optional<Song> getSongById(long id);

}
