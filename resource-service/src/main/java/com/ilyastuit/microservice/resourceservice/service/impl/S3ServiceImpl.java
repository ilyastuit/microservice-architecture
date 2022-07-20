package com.ilyastuit.microservice.resourceservice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.ilyastuit.microservice.resourceservice.service.S3Service;
import com.ilyastuit.microservice.resourceservice.service.exception.DomainException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@Service
public class S3ServiceImpl implements S3Service {

    private static final Log LOG = LogFactory.getLog(S3ServiceImpl.class);

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    public S3ServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public String storeFile(MultipartFile fileFromRequest) {
        String originalFilename = fileFromRequest.getOriginalFilename();
        File file = convertMultiPartToFile(fileFromRequest);

        amazonS3.putObject(bucketName, originalFilename, file);

        deleteFile(file);

        return originalFilename;
    }

    @Override
    public byte[] getFile(String fileName) {
        S3Object object = amazonS3.getObject(bucketName, fileName);
        S3ObjectInputStream objectContent = object.getObjectContent();
        try {
            return IOUtils.toByteArray(objectContent);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new DomainException(e.getMessage());
        }
    }

    @Override
    public void deleteFiles(List<String> fileNames) {
        for (String fileName : fileNames) {
            amazonS3.deleteObject(bucketName, fileName);
        }
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

    private void deleteFile(File file) {
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new DomainException(e.getMessage());
        }
    }

}
