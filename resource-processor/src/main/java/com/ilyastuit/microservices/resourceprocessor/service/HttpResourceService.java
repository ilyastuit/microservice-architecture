package com.ilyastuit.microservices.resourceprocessor.service;

import java.io.File;

public interface HttpResourceService {

    String RESOURCES_PATH = "resources";

    File downloadFile(long id);

}
