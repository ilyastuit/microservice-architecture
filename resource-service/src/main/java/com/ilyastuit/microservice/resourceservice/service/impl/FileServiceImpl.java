package com.ilyastuit.microservice.resourceservice.service.impl;

import com.ilyastuit.microservice.resourceservice.entity.Song;
import com.ilyastuit.microservice.resourceservice.repository.SongRepository;
import com.ilyastuit.microservice.resourceservice.service.FileService;
import com.ilyastuit.microservice.resourceservice.service.exception.NotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FileServiceImpl implements FileService {

    private static final Log LOG = LogFactory.getLog(FileServiceImpl.class);

    private final SongRepository songRepository;

    public FileServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Song save(String songName) {
        Song song = new Song(songName);
        return songRepository.save(song);
    }

    @Override
    public String getFileNameById(long id) {
        Optional<Song> song = songRepository.getSongById(id);

        if (song.isEmpty()) {
            String errorMessage = String.format("File not found. ID=%d", id);
            LOG.warn(errorMessage);
            throw new NotFoundException(errorMessage);
        }

        return song.get().getName();
    }

    @Override
    public List<String> getFileNamesByIds(Long[] ids) {
        return StreamSupport
                .stream(songRepository.findAllById(Arrays.asList(ids)).spliterator(), false)
                .map(Song::getName)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFiles(Long[] ids) {
        songRepository.deleteAllById(Arrays.asList(ids));
    }
}
