package com.ilyastuit.microservices.songservice.service.mapper;

import com.ilyastuit.microservices.songservice.entity.Song;
import com.ilyastuit.microservices.songservice.http.dto.SongDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper {

    Song songDTOToEntity(SongDTO songDTO);

    SongDTO entityToSongDTO(Song song);
}
