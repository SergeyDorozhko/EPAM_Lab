package com.epam.lab.dto;

import java.io.Serializable;

public abstract class AbstractDTO implements Serializable {
    private long id;

    public AbstractDTO(){    }

    public AbstractDTO(int id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
