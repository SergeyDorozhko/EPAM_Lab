package com.epam.lab.dto.mapper;

import com.epam.lab.dto.AbstractDTO;
import com.epam.lab.model.Bean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public abstract class AbstractMapper<T extends Bean, K extends AbstractDTO> implements Mapper {
    private ModelMapper modelMapper;

    private Class<T> beanClass;
    private Class<K> dtoClass;


    @Autowired
    public AbstractMapper(Class<T> beanClass, Class<K> dtoClass, ModelMapper modelMapper) {
        this.beanClass = beanClass;
        this.dtoClass = dtoClass;
        this.modelMapper = modelMapper;
    }

    public T toBean(AbstractDTO dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, beanClass);
    }

    public K toDTO(Bean bean) {
        return Objects.isNull(bean) ? null : modelMapper.map(bean, dtoClass);
    }
}
