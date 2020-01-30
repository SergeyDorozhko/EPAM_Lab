package com.epam.lab.dto;

import java.io.Serializable;

public abstract class AbstractDTO implements Serializable {
    private int id;

    public AbstractDTO(){    }

    public AbstractDTO(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
