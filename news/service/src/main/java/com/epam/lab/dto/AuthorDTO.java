package com.epam.lab.dto;

import org.springframework.stereotype.Component;

@Component
public class AuthorDTO extends AbstractDTO{
    private String name;
    private String surname;

    public AuthorDTO(){
        super();
    }

    public AuthorDTO(int id, String name, String surname) {
        super(id);
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
