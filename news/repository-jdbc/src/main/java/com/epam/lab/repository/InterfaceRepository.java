package com.epam.lab.repository;

import com.epam.lab.model.Bean;

public interface InterfaceRepository<T extends Bean> {

    T create(T bean);

    boolean delete(long id);

    T update(T bean);

    T findBy(long id);
}
