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
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AuthorDTO authorDTO = (AuthorDTO) o;

        if (name != null ? !name.equals(authorDTO.name) : authorDTO.name != null) return false;
        return surname != null ? surname.equals(authorDTO.surname) : authorDTO.surname == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }
}
