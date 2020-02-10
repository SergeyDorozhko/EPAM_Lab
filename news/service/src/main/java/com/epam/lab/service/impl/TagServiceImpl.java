package com.epam.lab.service.impl;

import com.epam.lab.dto.Mapper.TagMapper;
import com.epam.lab.dto.TagDTO;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.service.TagService;
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

    /**
     * Check has database tag with the same name,  if this tag unique create new tag otherwise throws ServiceException.
     * @param bean TagDTO with name of new tag.
     * @return tagDTO with name and generated id.
     */
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

    /**
     * Check has database other tag with the new name of this tag,  if this name unique update tag otherwise throws ServiceException.
     * @param bean TagDTO with new name and id of tag.
     * @return tagDTO with id and new name.
     */
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
