package com.ilyastuit.microservices.songservice.service.impl;

import com.ilyastuit.microservices.songservice.entity.Song;
import com.ilyastuit.microservices.songservice.repository.SongRepository;
import com.ilyastuit.microservices.songservice.service.SongService;
import com.ilyastuit.microservices.songservice.service.exception.NotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private static final Log LOG = LogFactory.getLog(SongServiceImpl.class);

    private final SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Song save(Song song) {
        return songRepository.save(song);
    }

    @Override
    public Song getById(long id) {
        Optional<Song> song = songRepository.findById(id);

        if (song.isEmpty()) {
            String errorMessage = String.format("Song with id=%s not found.", id);
            LOG.warn(errorMessage);
            throw new NotFoundException(errorMessage);
        }

        return song.get();
    }

    @Override
    public void deleteAll(Long[] ids) {
        songRepository.deleteAllById(Arrays.asList(ids));
    }
}
