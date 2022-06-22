package com.ilyastuit.microservices.resourceprocessor.service;

import com.ilyastuit.microservices.resourceprocessor.dto.SongDTO;
import org.springframework.web.multipart.MultipartFile;

public interface Mp3Processor {

    SongDTO getMetadataFromFile(MultipartFile mp3File, long resourceId);

}
