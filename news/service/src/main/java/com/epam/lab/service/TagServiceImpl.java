package com.epam.lab.service;

import com.epam.lab.dto.Mapper.TagMapper;
import com.epam.lab.dto.TagDTO;
import com.epam.lab.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return mapper.toDTO(
                repository.create(
                        mapper.toBean(bean)));
    }

    @Override
    public boolean delete(long id) {
        return repository.delete(id);
    }

    @Override
    public TagDTO update(TagDTO bean) {
        return mapper.toDTO(
                repository.update(
                        mapper.toBean(bean)));
    }

    @Override
    public TagDTO findById(long id) {
        return mapper.toDTO(repository.findBy(id));
    }
}
