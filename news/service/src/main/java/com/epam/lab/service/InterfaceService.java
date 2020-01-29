package com.epam.lab.service;


import com.epam.lab.model.Bean;

import java.util.List;

public interface InterfaceService <K, T extends Bean> {

    T create(T bean);

    boolean delete(K id);

    T update(T bean);

    List<T> findAll();

    T findById(K id);
}
