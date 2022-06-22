package com.ilyastuit.microservices.songservice.http;

import com.ilyastuit.microservices.songservice.entity.Song;
import com.ilyastuit.microservices.songservice.http.dto.SongDTO;
import com.ilyastuit.microservices.songservice.service.SongService;
import com.ilyastuit.microservices.songservice.service.exception.NotFoundException;
import com.ilyastuit.microservices.songservice.service.mapper.SongMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/songs")
public class MainController {

    private final SongService songService;
    private final SongMapper songMapper;

    public MainController(SongService songService, SongMapper songMapper) {
        this.songService = songService;
        this.songMapper = songMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SongDTO> create(@RequestBody @Valid SongDTO songDTO) {

        Song song = songService.save(songDTO);

        return new ResponseEntity<>(songMapper.entityToSongDTO(song), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SongDTO> getById(@PathVariable long id) {
        return new ResponseEntity<>(songMapper.entityToSongDTO(songService.getById(id)), HttpStatus.OK);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long[]> deleteSongs(@RequestParam Long[] ids) {
        try {
            songService.deleteAll(ids);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("Song not found.");
        }

        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

}
