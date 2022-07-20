package com.ilyastuit.microservice.resourceservice.http;

import com.ilyastuit.microservice.resourceservice.http.dto.SongDTO;
import com.ilyastuit.microservice.resourceservice.messaging.event.ResourceCreatedEvent;
import com.ilyastuit.microservice.resourceservice.service.FileService;
import com.ilyastuit.microservice.resourceservice.service.S3Service;
import com.ilyastuit.microservice.resourceservice.service.mapper.SongMapper;
import com.ilyastuit.microservice.resourceservice.service.validation.ValidMediaType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/resources")
public class MainController {

    private static final Log log = LogFactory.getLog(MainController.class);

    @Value("${kafka.producer.name}")
    private String topicName;
    private final S3Service s3Service;
    private final FileService fileService;
    private final SongMapper songMapper;

    private final KafkaTemplate<String, ResourceCreatedEvent> kafkaTemplate;

    public MainController(S3Service s3Service, FileService fileService, SongMapper songMapper, KafkaTemplate<String, ResourceCreatedEvent> kafkaTemplate) {
        this.s3Service = s3Service;
        this.fileService = fileService;
        this.songMapper = songMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SongDTO> store(@RequestParam("song-file") @ValidMediaType("audio/mpeg") MultipartFile songFile) {
        log.info("Creating resource with file name = %s".formatted(songFile.getName()));
        String songName = s3Service.storeFile(songFile);

        SongDTO songDTO = songMapper.entityToSongDTO(
                fileService.save(songName)
        );

        kafkaTemplate.send(topicName, new ResourceCreatedEvent(songDTO.getId()));

        return new ResponseEntity<>(songDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> download(@PathVariable @Positive long id) {
        String fileName = fileService.getFileNameById(id);

        return new ResponseEntity<>(s3Service.getFile(fileName), HttpStatus.OK);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long[]> deleteFiles(@RequestParam Long[] ids) {
        s3Service.deleteFiles(fileService.getFileNamesByIds(ids));
        fileService.deleteFiles(ids);

        return new ResponseEntity<>(ids, HttpStatus.OK);
    }
}
