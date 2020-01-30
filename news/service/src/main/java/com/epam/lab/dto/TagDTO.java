package com.epam.lab.dto;

import org.springframework.stereotype.Component;

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
}
