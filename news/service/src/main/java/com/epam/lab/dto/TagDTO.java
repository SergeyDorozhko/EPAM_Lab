package com.epam.lab.dto;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TagDTO extends AbstractDTO {
    private String name;

    public TagDTO() {
        super();
    }

    public TagDTO(int id, String name) {
        super(id);
        this.name = name;
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

        TagDTO tagDTO = (TagDTO) o;

        return name != null ? name.equals(tagDTO.name) : tagDTO.name == null;
    }

    @Override
    public int hashCode() {
        return (name != null ? name.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "TagDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
