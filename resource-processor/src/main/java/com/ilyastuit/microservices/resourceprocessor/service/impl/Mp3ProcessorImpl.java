package com.ilyastuit.microservices.resourceprocessor.service.impl;

import com.ilyastuit.microservices.resourceprocessor.dto.SongDTO;
import com.ilyastuit.microservices.resourceprocessor.service.Mp3Processor;
import com.ilyastuit.microservices.resourceprocessor.service.exception.DomainException;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
public class Mp3ProcessorImpl implements Mp3Processor {

    private static final Log LOG = LogFactory.getLog(Mp3ProcessorImpl.class);

    @Override
    public SongDTO getMetadataFromFile(MultipartFile mp3File, long resourceId) {
        Mp3File mp3;

        try {
            mp3 = new Mp3File(convertMultiPartToFile(mp3File));
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            LOG.error(e.getMessage());
            throw new DomainException(e.getMessage());
        }

        SongDTO songDTO = new SongDTO();
        fillMp3Metadata(songDTO, mp3.getId3v2Tag());
        songDTO.setResourceId(resourceId);

        return songDTO;
    }

    private void fillMp3Metadata(SongDTO songDTO, ID3v2 id3v2Tag) {
        songDTO.setName(String.format("%s - %s", id3v2Tag.getArtistUrl(), id3v2Tag.getTitle()));
        songDTO.setArtist(id3v2Tag.getAlbumArtist());
        songDTO.setAlbum(id3v2Tag.getAlbum());
        songDTO.setLength(id3v2Tag.getLength());
        songDTO.setYear(Integer.parseInt(id3v2Tag.getYear()));
    }

    private File convertMultiPartToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new DomainException(e.getMessage());
        }
        return convertedFile;
    }

}
