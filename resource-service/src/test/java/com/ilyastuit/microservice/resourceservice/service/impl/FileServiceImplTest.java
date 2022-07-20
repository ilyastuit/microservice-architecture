package com.ilyastuit.microservice.resourceservice.service.impl;

import com.ilyastuit.microservice.resourceservice.entity.Song;
import com.ilyastuit.microservice.resourceservice.repository.SongRepository;
import com.ilyastuit.microservice.resourceservice.service.builder.SongBuilder;
import com.ilyastuit.microservice.resourceservice.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private FileServiceImpl fileService;

    private static final SongBuilder SONG_BUILDER = new SongBuilder();

    @Test
    void successSaveSong() {
        Song expected = SONG_BUILDER.build();

        when(songRepository.save(expected))
                .thenReturn(expected);

        Song actual = fileService.save(SongBuilder.VALID_NAME);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void successGetFileNameById() {
        long id = 1;
        Song expected = SONG_BUILDER.build();

        when(songRepository.getSongById(id))
                .thenReturn(Optional.of(expected));

        String actual = fileService.getFileNameById(id);

        assertEquals(expected.getName(), actual);
    }

    @Test
    void GetFileNameBy_NotExistId_ThrowsException() {
        long id = -1;
        when(songRepository.getSongById(id))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
           fileService.getFileNameById(id);
        });
    }

    @Test
    void successGetFileNamesByIds() {
        Long[] ids = {1L};
        Song songs = SONG_BUILDER.build();
        String expected = SongBuilder.VALID_NAME;

        when(songRepository.findAllById(Arrays.asList(ids)))
                .thenReturn(List.of(songs));

        List<String> actual = fileService.getFileNamesByIds(ids);

        assertNotEquals(0, actual.size());
        assertEquals(expected, actual.get(0));
    }

    @Test
    void successGetFileNamesByIds_ReturnEmptyList() {
        Long[] ids = {-1L};

        when(songRepository.findAllById(Arrays.asList(ids)))
                .thenReturn(List.of());

        List<String> actual = fileService.getFileNamesByIds(ids);

        assertEquals(0, actual.size());
    }

    @Test
    void successDeleteFiles() {
        Long[] ids = {1L};

        fileService.deleteFiles(ids);

        verify(songRepository, times(1))
                .deleteAllById(Arrays.asList(ids));
    }
}