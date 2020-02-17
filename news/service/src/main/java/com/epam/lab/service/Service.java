package com.epam.lab.service;


import com.epam.lab.dto.AbstractDTO;

public interface Service<T extends AbstractDTO> {

    T create(T bean);

    boolean delete(long id);

    T update(T bean);

    T findById(long id);
}
