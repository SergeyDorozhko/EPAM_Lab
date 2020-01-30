package com.epam.lab.model;

import org.springframework.stereotype.Component;

@Component
public class Tag extends Bean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
