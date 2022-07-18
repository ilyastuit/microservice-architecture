package com.ilyastuit.microservices.resourceprocessor.service.impl;

import com.ilyastuit.microservices.resourceprocessor.dto.SongMetaDataDTO;
import com.ilyastuit.microservices.resourceprocessor.service.Mp3Processor;
import com.ilyastuit.microservices.resourceprocessor.service.exception.DomainException;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class Mp3ProcessorImpl implements Mp3Processor {

    private static final Log log = LogFactory.getLog(Mp3ProcessorImpl.class);

    @Override
    public SongMetaDataDTO getMetadataFromFile(File mp3File) {
        log.info("Processing new file metadata with name = %s".formatted(mp3File.getName()));
        Mp3File mp3;

        try {
            mp3 = new Mp3File(mp3File);
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            throw new DomainException(e.getMessage());
        }

        return getMp3Metadata(mp3.getId3v2Tag());
    }

    private SongMetaDataDTO getMp3Metadata(ID3v2 id3v2Tag) {
        SongMetaDataDTO songMetaDataDTO = new SongMetaDataDTO();
        songMetaDataDTO.setName(id3v2Tag.getTitle());
        songMetaDataDTO.setArtist(id3v2Tag.getAlbumArtist());
        songMetaDataDTO.setAlbum(id3v2Tag.getAlbum());
        songMetaDataDTO.setLength(id3v2Tag.getLength());
        songMetaDataDTO.setYear(Integer.parseInt(id3v2Tag.getYear()));
        return songMetaDataDTO;
    }

}
