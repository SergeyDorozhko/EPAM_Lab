package com.epam.lab.service;

import com.epam.lab.dto.Mapper.TagMapper;
import com.epam.lab.dto.TagDTO;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    private TagMapper mapper;
    private TagRepository repository;

    @Autowired
    public TagServiceImpl(TagMapper mapper, TagRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public TagDTO create(TagDTO bean) {
        if (tagExist(bean.getName())) {
            throw new ServiceException("this tag already exist");
        }
        return mapper.toDTO(
                repository.create(
                        mapper.toBean(bean)));
    }

    private boolean tagExist(String name){
        boolean isExist = true;
        try {
            repository.findBy(name);
        } catch (EmptyResultDataAccessException ex) {
            isExist = false;
        }
        return isExist;
    }

    @Override
    public boolean delete(long id) {
        return repository.delete(id);
    }

    @Override
    public TagDTO update(TagDTO bean) {
        if (tagExist(bean.getName())) {
            throw new ServiceException("this tag already exist");
        }
        return mapper.toDTO(
                repository.update(
                        mapper.toBean(bean)));
    }

    @Override
    public TagDTO findById(long id) {
        return mapper.toDTO(repository.findBy(id));
    }
}
