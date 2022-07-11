package com.ilyastuit.microservices.resourceprocessor.service;

import java.io.File;

public interface HttpResourceService {

    public static final String RESOURCES_PATH = "resources";

    File downloadFile(long id);

}
