package com.ilyastuit.microservice.resourceservice.http.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class SongDTO {
    private long id;
    @NotBlank
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongDTO songDTO = (SongDTO) o;
        return id == songDTO.id &&
                Objects.equals(name, songDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "SongDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
