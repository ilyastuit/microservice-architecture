package com.ilyastuit.microservices.songservice.service.impl;

import com.ilyastuit.microservices.songservice.builder.SongBuilder;
import com.ilyastuit.microservices.songservice.entity.Song;
import com.ilyastuit.microservices.songservice.repository.SongRepository;
import com.ilyastuit.microservices.songservice.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SongServiceImplTest {

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private SongServiceImpl songService;

    private static final SongBuilder SONG_BUILDER = new SongBuilder();

    @Test
    void successSaveSong() {
        var song = SONG_BUILDER.build();
        songService.save(song);

        verify(songRepository, times(1))
                .save(song);
    }

    @Test
    void successGetById() {
        long id = 1;
        var song = SONG_BUILDER.build();

        when(songRepository.findById(id))
                .thenReturn(Optional.of(song));

        Song actual = songService.getById(id);

        assertEquals(song.getName(), actual.getName());
        assertEquals(song.getAlbum(), actual.getAlbum());
        assertEquals(song.getArtist(), actual.getArtist());
        assertEquals(song.getLength(), actual.getLength());
        assertEquals(song.getResourceId(), actual.getResourceId());
        assertEquals(song.getYear(), actual.getYear());
    }

    @Test
    void getBy_NotExistId() {
        long id = -1;

        when(songRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            songService.getById(id);
        });
    }

    @Test
    void successDeleteAllByIds() {
        Long[] ids = {1L};
        songService.deleteAll(ids);

        verify(songRepository, times(1))
                .deleteAllById(List.of(ids));
    }
}