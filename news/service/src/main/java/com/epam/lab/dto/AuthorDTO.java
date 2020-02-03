package com.epam.lab.dto;

import org.springframework.stereotype.Component;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorDTO)) return false;
        if (!super.equals(o)) return false;
        AuthorDTO authorDTO = (AuthorDTO) o;
        return Objects.equals(name, authorDTO.name) &&
                Objects.equals(surname, authorDTO.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname);
    }
}
