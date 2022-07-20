package com.ilyastuit.microservice.resourceservice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:/application-test.yaml")
class S3ServiceImplTest {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private S3ServiceImpl s3Service;

    @Test
    void successStoreFile() {
        MockMultipartFile testFile = new MockMultipartFile(
                "test",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "test".getBytes()
        );

        String actual = s3Service.storeFile(testFile);

        assertEquals(testFile.getOriginalFilename(), actual);
        verify(amazonS3, times(1))
                .putObject(eq(bucketName), eq(testFile.getOriginalFilename()), any(File.class));
    }

    @Test
    void successGetFile() throws IOException {
        String fileName = "test";
        S3Object s3Object = new S3Object();
        FileInputStream fileInputStream = new FileInputStream(createTestFile());
        s3Object.setObjectContent(fileInputStream);

        when(amazonS3.getObject(bucketName, fileName))
                .thenReturn(s3Object);

        byte[] actual = s3Service.getFile(fileName);

        assertEquals(fileInputStream.readAllBytes().length, actual.length);
    }

    @Test
    void successDeleteFiles() {
        String fileName = "test";

        s3Service.deleteFiles(List.of(fileName));

        verify(amazonS3, times(1))
                .deleteObject(bucketName, fileName);
    }

    private File createTestFile() throws IOException {
        File testFile = File.createTempFile("test-download", ".mp3");
        testFile.deleteOnExit();
        return testFile;
    }
}