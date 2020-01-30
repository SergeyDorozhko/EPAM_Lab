package com.epam.lab.dto.Mapper;

import com.epam.lab.dto.AbstractDTO;
import com.epam.lab.model.Bean;

public interface Mapper <K extends AbstractDTO, T extends Bean> {
    K toDTO(T bean);
    T toBean(K dto);
}
