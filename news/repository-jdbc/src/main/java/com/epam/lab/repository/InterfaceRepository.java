package com.epam.lab.repository;

import com.epam.lab.model.Bean;

import java.util.List;

public interface InterfaceRepository<T extends Bean> {

    T create(T bean);

    boolean delete(int id);

    T update(T bean);

    List<T> findAll();

    T findById(int id);
}
