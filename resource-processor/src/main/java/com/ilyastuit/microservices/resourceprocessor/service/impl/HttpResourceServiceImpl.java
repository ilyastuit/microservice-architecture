package com.ilyastuit.microservices.resourceprocessor.service.impl;

import com.ilyastuit.microservices.resourceprocessor.service.HttpResourceService;
import com.ilyastuit.microservices.resourceprocessor.service.exception.DomainException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class HttpResourceServiceImpl implements HttpResourceService {

    private static final Log LOG = LogFactory.getLog(HttpResourceServiceImpl.class);
    @Value("${services.resource-service.url}")
    private String resourceServiceUrl;

    private final RestTemplate restTemplate;

    public HttpResourceServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public File downloadFile(long id) {
        File file;
        try {
            file = restTemplate.execute("%s/%s/%d".formatted(resourceServiceUrl, RESOURCES_PATH, id), HttpMethod.GET, null, clientHttpResponse -> {
                File ret = File.createTempFile("download", ".mp3");
                StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
                return ret;
            });
        } catch (HttpStatusCodeException e) {
            LOG.error(e.getMessage());
            throw new DomainException(e.getMessage());
        }

        if (file == null) {
            String errorMessage = String.format("Couldn't download thw file, resourceId=%d", id);
            LOG.error(errorMessage);
            throw new DomainException(errorMessage);
        }

        return file;
    }
}
