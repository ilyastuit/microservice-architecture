package com.ilyastuit.microservices.resourceprocessor.service.impl;

import com.ilyastuit.microservices.resourceprocessor.dto.SongDTO;
import com.ilyastuit.microservices.resourceprocessor.service.HttpSongService;
import com.ilyastuit.microservices.resourceprocessor.service.exception.DomainException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.StringJoiner;

@Service
public class HttpSongServiceImpl implements HttpSongService {

    private static final Log LOG = LogFactory.getLog(HttpSongServiceImpl.class);

    @Value(("${services.song-service.url}"))
    private String songServiceUrl;

    private final RestTemplate restTemplate;

    public HttpSongServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void createSong(SongDTO songDTO) {
        RequestEntity<SongDTO> body = RequestEntity
                .post(String.format("%s/%s", songServiceUrl, SONGS_PATH))
                .body(songDTO, SongDTO.class);

        ResponseEntity<SongDTO> response;
        try {
            response = restTemplate.exchange(body, SongDTO.class);
        } catch (HttpStatusCodeException e) {
            LOG.error(e.getMessage());
            throw new DomainException(e.getMessage());
        }

        if (!response.getStatusCode().is2xxSuccessful() || !response.hasBody()) {
            String errorMessage = String.format("Couldn't create song. Response=%s", response.toString());
            LOG.error(errorMessage);
            throw new DomainException(errorMessage);
        }
        LOG.info(String.format("Song has been created. id=%d", response.getBody().getId()));
    }

    @Override
    public void deleteSong(long... ids) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (long id : ids) {
            stringJoiner.add(String.valueOf(id));
        }
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(String.format("%s/%s", songServiceUrl, SONGS_PATH))
                .queryParam("ids", stringJoiner.toString()).toUriString();
        try {
            restTemplate.delete(urlTemplate);
        } catch (HttpStatusCodeException e) {
            LOG.error(e.getMessage());
            throw new DomainException(e.getMessage());
        }
        LOG.info(String.format("Songs have been deleted. ids=%s", stringJoiner.toString()));
    }
}
