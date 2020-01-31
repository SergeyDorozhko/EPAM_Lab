package com.epam.lab.model;

import java.io.Serializable;

public abstract class Bean implements Serializable {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
