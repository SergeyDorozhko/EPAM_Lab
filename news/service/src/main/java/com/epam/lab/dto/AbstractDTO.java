package com.epam.lab.dto;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractDTO)) return false;
        AbstractDTO that = (AbstractDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
