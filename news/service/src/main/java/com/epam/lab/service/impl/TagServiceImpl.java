package com.epam.lab.service.impl;

import com.epam.lab.dto.TagDTO;
import com.epam.lab.dto.mapper.TagMapper;
import com.epam.lab.exception.DuplicateTagException;
import com.epam.lab.exception.RepositoryException;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
     *
     * @param bean TagDTO with name of new tag.
     * @return tagDTO with name and generated id.
     */
    @Transactional
    @Override
    public TagDTO create(TagDTO bean) {
        checkTag(bean.getName());
        return mapper.toDTO(
                repository.create(
                        mapper.toBean(bean)));
    }

    private void checkTag(String name) {
        try {
            repository.findBy(name);
            throw new DuplicateTagException();
        } catch (EmptyResultDataAccessException | RepositoryException ex) {
            //TODO logger;
            System.err.println("new tag");
        }
    }


    @Transactional
    @Override
    public boolean delete(long id) {
        return repository.delete(id);
    }

    /**
     * Check has database other tag with the new name of this tag,
     * if this name unique update tag otherwise throws ServiceException.
     *
     * @param bean TagDTO with new name and id of tag.
     * @return tagDTO with id and new name.
     */
    @Transactional
    @Override
    public TagDTO update(TagDTO bean) {
        checkTag(bean.getName());
        return mapper.toDTO(
                repository.update(
                        mapper.toBean(bean)));
    }

    @Override
    public TagDTO findById(long id) {
        return mapper.toDTO(repository.findBy(id));
    }

    @Override
    public List<TagDTO> findAll() {
        return repository.findAll().stream().map(c -> mapper.toDTO(c)).collect(Collectors.toList());
    }
}
