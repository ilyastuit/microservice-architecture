package com.ilyastuit.microservices.resourceprocessor.messaging;

import com.ilyastuit.microservices.resourceprocessor.dto.SongMetaDataDTO;
import com.ilyastuit.microservices.resourceprocessor.messaging.event.ResourceCreatedEvent;
import com.ilyastuit.microservices.resourceprocessor.service.HttpResourceService;
import com.ilyastuit.microservices.resourceprocessor.service.HttpSongService;
import com.ilyastuit.microservices.resourceprocessor.service.Mp3Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ResourceListener {

    private static final Log log = LogFactory.getLog(ResourceListener.class);

    private final HttpResourceService httpResourceService;
    private final HttpSongService httpSongService;
    private final Mp3Processor mp3Processor;

    public ResourceListener(HttpResourceService httpResourceService, HttpSongService httpSongService, Mp3Processor mp3Processor) {
        this.httpResourceService = httpResourceService;
        this.httpSongService = httpSongService;
        this.mp3Processor = mp3Processor;
    }

    @KafkaListener(topics = "${kafka.producer.name}", containerFactory = "singleFactory")
    public void consume(ResourceCreatedEvent resourceCreatedEvent) {
        log.info("Received resource with id = %d".formatted(resourceCreatedEvent.getId()));

        File resourceFile = httpResourceService.downloadFile(resourceCreatedEvent.getId());
        SongMetaDataDTO songMetaData = mp3Processor.getMetadataFromFile(resourceFile);
        songMetaData.setResourceId(resourceCreatedEvent.getId());

        httpSongService.createSong(songMetaData);
    }
}