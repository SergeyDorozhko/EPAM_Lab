package com.epam.lab.model;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @Column(name = "user_id")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    ERole ERole;

    @OneToOne
    @MapsId
    private User user;

    public ERole getERole() {
        return ERole;
    }

    public void setERole(ERole ERole) {
        this.ERole = ERole;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
