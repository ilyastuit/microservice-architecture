package com.ilyastuit.microservices.resourceprocessor.service.impl;

import com.ilyastuit.microservices.resourceprocessor.service.HttpResourceService;
import com.ilyastuit.microservices.resourceprocessor.service.exception.DomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

import static com.ilyastuit.microservices.resourceprocessor.service.HttpResourceService.RESOURCES_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:/application-test.yaml")
class HttpResourceServiceImplTest {

    @Value("${services.resource-service.url}")
    private String resourceServiceUrl;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private HttpResourceService httpResourceService;

    @Test
    void successDownload() throws IOException {
        long testId = 1;
        File testFile = createTestFile();

        when(restTemplate.execute(
                eq("%s/%s/%d".formatted(resourceServiceUrl, RESOURCES_PATH, testId)),
                eq(HttpMethod.GET),
                any(),
                any())
        ).thenReturn(testFile);

        File file = httpResourceService.downloadFile(testId);

        assertEquals(file.getAbsolutePath(), testFile.getAbsolutePath());
        assertEquals(file, testFile);
    }

    @Test
    void invalidIdDownloadThrowsException() {
        long invalidId = -1;

        when(restTemplate.execute(
                eq("%s/%s/%d".formatted(resourceServiceUrl, RESOURCES_PATH, invalidId)),
                eq(HttpMethod.GET),
                any(),
                any())
        ).thenThrow(DomainException.class);

        assertThrows(DomainException.class, () -> {
            httpResourceService.downloadFile(invalidId);
        });
    }

    private File createTestFile() throws IOException {
        File testFile = File.createTempFile("test-download", ".mp3");
        testFile.deleteOnExit();
        return testFile;
    }
}