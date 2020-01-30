package com.epam.lab.model;

import java.io.Serializable;

public abstract class Bean implements Serializable {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
