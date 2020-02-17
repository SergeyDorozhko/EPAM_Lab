package com.epam.lab.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

public abstract class AbstractDTO implements Serializable {
    @PositiveOrZero(message = "Id can't be negative")
    private long id;

    public AbstractDTO() {
    }

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
        if (o == null || getClass() != o.getClass()) return false;

        AbstractDTO that = (AbstractDTO) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
