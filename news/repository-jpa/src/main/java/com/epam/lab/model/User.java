package com.epam.lab.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity()
@Table(name = "users")
public class User extends Bean implements Serializable {
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String login;
    @Column
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Roles role;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
