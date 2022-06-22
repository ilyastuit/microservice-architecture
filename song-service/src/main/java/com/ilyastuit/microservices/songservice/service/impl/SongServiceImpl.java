package com.ilyastuit.microservices.songservice.service.impl;

import com.ilyastuit.microservices.songservice.entity.Song;
import com.ilyastuit.microservices.songservice.http.dto.SongDTO;
import com.ilyastuit.microservices.songservice.repository.SongRepository;
import com.ilyastuit.microservices.songservice.service.SongService;
import com.ilyastuit.microservices.songservice.service.exception.NotFoundException;
import com.ilyastuit.microservices.songservice.service.mapper.SongMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private static final Log LOG = LogFactory.getLog(SongServiceImpl.class);

    private final SongRepository songRepository;
    private final SongMapper songMapper;

    public SongServiceImpl(SongRepository songRepository, SongMapper songMapper) {
        this.songRepository = songRepository;
        this.songMapper = songMapper;
    }

    @Override
    public Song save(SongDTO songDTO) {
        return songRepository.save(songMapper.songDTOToEntity(songDTO));
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
