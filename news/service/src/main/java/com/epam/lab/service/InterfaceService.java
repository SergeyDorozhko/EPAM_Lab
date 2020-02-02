package com.epam.lab.service;


import com.epam.lab.dto.AbstractDTO;
import com.epam.lab.model.Bean;

import java.util.List;

public interface InterfaceService <T extends AbstractDTO> {

    T create(T bean);

    boolean delete(int id);

    T update(T bean);

    T findById(int id);
}
