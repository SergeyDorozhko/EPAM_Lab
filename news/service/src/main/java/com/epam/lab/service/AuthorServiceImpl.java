package com.epam.lab.service;

import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.dto.Mapper.AuthorMapper;
import com.epam.lab.repository.AuthorRepositoryImpl;
import com.epam.lab.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("authorService")
public class AuthorServiceImpl implements AuthorService {

    private AuthorMapper mapper;

    private AuthorRepositoryImpl repository;
    private NewsRepository newsRepository;

    @Autowired
    public AuthorServiceImpl(AuthorMapper mapper, AuthorRepositoryImpl repository, NewsRepository newsRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.newsRepository = newsRepository;
    }

    @Override
    public AuthorDTO create(AuthorDTO bean) {
        return mapper.toDTO(repository.create(mapper.toBean(bean)));

    }

    @Override
    public boolean delete(long id) {
        List<Long> newsList = newsRepository.findNewsIdByAuthor(id);
        for (Long newsId : newsList) {
            newsRepository.delete(newsId);
        }
        return repository.delete(id);
    }

    @Override
    public AuthorDTO update(AuthorDTO bean) {
        return mapper.toDTO(repository.update(mapper.toBean(bean)));
    }


    @Override
    public AuthorDTO findById(long id) {
        return mapper.toDTO(repository.findBy(id));
    }
}
