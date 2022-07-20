package com.ilyastuit.microservice.resourceservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3Service {

    String storeFile(MultipartFile file);

    byte[] getFile(String fileName);

    void deleteFiles(List<String> fileNames);
}
