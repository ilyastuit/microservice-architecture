package com.ilyastuit.microservices.resourceprocessor.service.impl;

import com.ilyastuit.microservices.resourceprocessor.dto.SongMetaDataDTO;
import com.ilyastuit.microservices.resourceprocessor.service.Mp3Processor;
import com.ilyastuit.microservices.resourceprocessor.service.exception.DomainException;
import com.ilyastuit.microservices.resourceprocessor.builder.SongMetaDataDTOBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Mp3ProcessorImplTest {

    @Mock
    private Mp3Processor mp3Processor;

    private final static SongMetaDataDTOBuilder SONG_META_DATA_DTO_BUILDER = new SongMetaDataDTOBuilder();

    @Test
    void successGetMetadataFromFile() {
        File testFile = getTestSongFile();

        var expected = SONG_META_DATA_DTO_BUILDER.build();

        when(mp3Processor.getMetadataFromFile(testFile))
                .thenReturn(expected);

        SongMetaDataDTO result = mp3Processor.getMetadataFromFile(testFile);

        assertEquals(result.getId(), SongMetaDataDTOBuilder.VALID_SONG_DTO_ID);
        assertEquals(result.getAlbum(), SongMetaDataDTOBuilder.VALID_ALBUM);
        assertEquals(result.getArtist(), SongMetaDataDTOBuilder.VALID_ARTIST);
        assertEquals(result.getName(), SongMetaDataDTOBuilder.VALID_NAME);
        assertEquals(result.getLength(), SongMetaDataDTOBuilder.VALID_LENGTH);
        assertEquals(result.getResourceId(), SongMetaDataDTOBuilder.VALID_RESOURCE_ID);
        assertEquals(result.getYear(), SongMetaDataDTOBuilder.VALID_YEAR);
    }

    @Test
    void getMetadataFromFileThrowsIOException() {
        File testFile = getTestSongFile();

        when(mp3Processor.getMetadataFromFile(testFile))
                .thenThrow(DomainException.class);

        assertThrows(DomainException.class, () -> {
            mp3Processor.getMetadataFromFile(testFile);
        });
    }

    private File getTestSongFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("twenty-one-pilots-stressed-out.mp3").getFile());
    }
}