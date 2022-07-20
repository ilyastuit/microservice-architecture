package com.ilyastuit.microservices.resourceprocessor.messaging;

import com.ilyastuit.microservices.resourceprocessor.builder.SongMetaDataDTOBuilder;
import com.ilyastuit.microservices.resourceprocessor.messaging.event.ResourceCreatedEvent;
import com.ilyastuit.microservices.resourceprocessor.service.HttpResourceService;
import com.ilyastuit.microservices.resourceprocessor.service.HttpSongService;
import com.ilyastuit.microservices.resourceprocessor.service.Mp3Processor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResourceListenerTest {

    @Mock
    private HttpResourceService httpResourceService;

    @Mock
    private HttpSongService httpSongService;

    @Mock
    private Mp3Processor mp3Processor;

    @InjectMocks
    private ResourceListener resourceListener;

    private static final SongMetaDataDTOBuilder SONG_META_DATA_DTO_BUILDER = new SongMetaDataDTOBuilder();

    @Test
    void successConsumeEvent() throws IOException {
        var event = new ResourceCreatedEvent(SongMetaDataDTOBuilder.VALID_RESOURCE_ID);
        var file = createTestFile();

        var songMetaDataDTO = SONG_META_DATA_DTO_BUILDER.build();

        when(httpResourceService.downloadFile(event.getId()))
                .thenReturn(file);

        when(mp3Processor.getMetadataFromFile(file))
                .thenReturn(songMetaDataDTO);

        resourceListener.consume(event);

        assertFalse(file.exists());
        verify(httpSongService, times(1))
                .createSong(songMetaDataDTO);
    }

    private File createTestFile() throws IOException {
        return File.createTempFile("test-download", ".mp3");
    }
}