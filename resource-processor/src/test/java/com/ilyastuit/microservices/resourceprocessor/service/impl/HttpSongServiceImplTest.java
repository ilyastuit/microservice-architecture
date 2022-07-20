package com.ilyastuit.microservices.resourceprocessor.service.impl;

import com.ilyastuit.microservices.resourceprocessor.dto.SongMetaDataDTO;
import com.ilyastuit.microservices.resourceprocessor.service.HttpSongService;
import com.ilyastuit.microservices.resourceprocessor.service.exception.DomainException;
import com.ilyastuit.microservices.resourceprocessor.builder.SongMetaDataDTOBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.StringJoiner;

import static com.ilyastuit.microservices.resourceprocessor.service.HttpSongService.SONGS_PATH;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("classpath:/application-test.yaml")
class HttpSongServiceImplTest {

    @Value(("${services.song-service.url}"))
    private String songServiceUrl;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private HttpSongService httpSongService;

    private static final SongMetaDataDTOBuilder META_DATA_DTO_BUILDER = new SongMetaDataDTOBuilder();

    @Test
    void successCreateSong() {
        var songMetaDataDTO = META_DATA_DTO_BUILDER.build();
        var body = RequestEntity
                .post(String.format("%s/%s", songServiceUrl, SONGS_PATH))
                .body(songMetaDataDTO, SongMetaDataDTO.class);
        var response = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(songMetaDataDTO);

        when(restTemplate.exchange(body, SongMetaDataDTO.class))
                .thenReturn(response);

        httpSongService.createSong(songMetaDataDTO);

        verify(restTemplate, times(1)).exchange(body, SongMetaDataDTO.class);
    }

    @Test
    void emptyResponseCreateSongThrowsException() {
        SongMetaDataDTO songMetaDataDTO = null;
        var body = RequestEntity
                .post(String.format("%s/%s", songServiceUrl, SONGS_PATH))
                .body(songMetaDataDTO, SongMetaDataDTO.class);
        var response = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(songMetaDataDTO);

        when(restTemplate.exchange(body, SongMetaDataDTO.class))
                .thenReturn(response);

        assertThrows(DomainException.class, () -> {
            httpSongService.createSong(songMetaDataDTO);
        });

        verify(restTemplate, times(1)).exchange(body, SongMetaDataDTO.class);
    }

    @Test
    void wrongStatusCodeCreateSongThrowsException() {
        SongMetaDataDTO songMetaDataDTO = META_DATA_DTO_BUILDER.build();
        var body = RequestEntity
                .post(String.format("%s/%s", songServiceUrl, SONGS_PATH))
                .body(songMetaDataDTO, SongMetaDataDTO.class);
        var response = ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(songMetaDataDTO);

        when(restTemplate.exchange(body, SongMetaDataDTO.class))
                .thenReturn(response);

        assertThrows(DomainException.class, () -> {
            httpSongService.createSong(songMetaDataDTO);
        });

        verify(restTemplate, times(1)).exchange(body, SongMetaDataDTO.class);
    }

    @Test
    void createSongRequestFailsWithClientError() {
        SongMetaDataDTO songMetaDataDTO = META_DATA_DTO_BUILDER.build();
        var body = RequestEntity
                .post(String.format("%s/%s", songServiceUrl, SONGS_PATH))
                .body(songMetaDataDTO, SongMetaDataDTO.class);

        when(restTemplate.exchange(body, SongMetaDataDTO.class))
                .thenThrow(HttpClientErrorException.class);

        assertThrows(DomainException.class, () -> {
            httpSongService.createSong(songMetaDataDTO);
        });

        verify(restTemplate, times(1)).exchange(body, SongMetaDataDTO.class);
    }

    @Test
    void createSongRequestFailsWithServerError() {
        SongMetaDataDTO songMetaDataDTO = META_DATA_DTO_BUILDER.build();
        var body = RequestEntity
                .post(String.format("%s/%s", songServiceUrl, SONGS_PATH))
                .body(songMetaDataDTO, SongMetaDataDTO.class);

        when(restTemplate.exchange(body, SongMetaDataDTO.class))
                .thenThrow(HttpServerErrorException.class);

        assertThrows(DomainException.class, () -> {
            httpSongService.createSong(songMetaDataDTO);
        });

        verify(restTemplate, times(1)).exchange(body, SongMetaDataDTO.class);
    }

    @Test
    void successDeleteSongWithIDArray() {
        long[] ids = {1, 2};

        StringJoiner stringJoiner = new StringJoiner(",");
        for (long id : ids) {
            stringJoiner.add(String.valueOf(id));
        }

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(String.format("%s/%s", songServiceUrl, SONGS_PATH))
                .queryParam("ids", stringJoiner.toString()).toUriString();

        httpSongService.deleteSong(ids);

        verify(restTemplate, times(1)).delete(urlTemplate);
    }

    @Test
    void successDeleteSongWithSingleID() {
        long id = SongMetaDataDTOBuilder.VALID_SONG_DTO_ID;

        StringJoiner stringJoiner = new StringJoiner(",");
        stringJoiner.add(String.valueOf(id));

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(String.format("%s/%s", songServiceUrl, SONGS_PATH))
                .queryParam("ids", stringJoiner.toString()).toUriString();

        httpSongService.deleteSong(id);
        verify(restTemplate, times(1)).delete(urlTemplate);
    }

    @Test
    void deleteSongRequestThrowsHttpException() {
        long id = SongMetaDataDTOBuilder.VALID_SONG_DTO_ID;

        StringJoiner stringJoiner = new StringJoiner(",");
        stringJoiner.add(String.valueOf(id));

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(String.format("%s/%s", songServiceUrl, SONGS_PATH))
                .queryParam("ids", stringJoiner.toString()).toUriString();

        doThrow(HttpClientErrorException.class)
                .when(restTemplate)
                .delete(urlTemplate);

        assertThrows(DomainException.class, () -> {
            httpSongService.deleteSong(id);
        });
    }
}