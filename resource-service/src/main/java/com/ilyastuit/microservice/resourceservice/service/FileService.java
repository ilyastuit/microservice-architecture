package com.ilyastuit.microservice.resourceservice.service;

import com.ilyastuit.microservice.resourceservice.entity.Song;

import java.util.List;

public interface FileService {
    
    Song save(String songLocation);

    String getFileNameById(long id);

    List<String> getFileNamesByIds(Long[] ids);

    void deleteFiles(Long[] ids);
}
