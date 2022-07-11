package com.ilyastuit.microservices.resourceprocessor.service;

import com.ilyastuit.microservices.resourceprocessor.dto.SongMetaDataDTO;

import java.io.File;

public interface Mp3Processor {

    SongMetaDataDTO getMetadataFromFile(File mp3File);

}
