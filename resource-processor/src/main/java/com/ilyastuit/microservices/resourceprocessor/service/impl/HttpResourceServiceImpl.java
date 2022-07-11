package com.ilyastuit.microservices.resourceprocessor.service.impl;

import com.ilyastuit.microservices.resourceprocessor.service.HttpResourceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class HttpResourceServiceImpl implements HttpResourceService {

    @Value("${services.resource-service.url}")
    private String resourceServiceUrl;

    private final RestTemplate restTemplate;

    public HttpResourceServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public File downloadFile(long id) {
        return restTemplate.execute("%s/%s/%d".formatted(resourceServiceUrl, RESOURCES_PATH, id), HttpMethod.GET, null, clientHttpResponse -> {
            File ret = File.createTempFile("download", ".mp3");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });
    }
}
