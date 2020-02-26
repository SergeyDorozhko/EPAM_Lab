package com.epam.lab.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AuthorDTO extends AbstractDTO{
    @NotNull
    @Pattern(regexp = "(^[A-Za-zА-Яа-я0-9-]+$)", message = "Name must consist of letters or '-'.")
    @Size(min = 1, max = 30, message = "Name size must be between 1 and 30.")
    private String name;
    @NotNull
    @Pattern(regexp = "(^[A-Za-zА-Яа-я0-9-]+$)", message = "Surname must consist of letters or '-'.")
    @Size(min = 1, max = 30, message = "Surname size must be between 1 and 30.")
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
