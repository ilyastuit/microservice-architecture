package com.ilyastuit.microservice.resourceservice.service.mapper;

import com.ilyastuit.microservice.resourceservice.entity.Song;
import com.ilyastuit.microservice.resourceservice.http.dto.SongDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper {

    Song songDTOToEntity(SongDTO songDTO);

    SongDTO entityToSongDTO(Song song);
}
