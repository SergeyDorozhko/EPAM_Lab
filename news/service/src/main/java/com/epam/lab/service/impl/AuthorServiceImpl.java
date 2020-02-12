package com.epam.lab.service.impl;

import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.dto.mapper.AuthorMapper;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("authorService")
public class AuthorServiceImpl implements AuthorService {

    private AuthorMapper mapper;

    private AuthorRepository repository;
    private NewsRepository newsRepository;

    @Autowired
    public AuthorServiceImpl(AuthorMapper mapper, AuthorRepository repository, NewsRepository newsRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.newsRepository = newsRepository;
    }

    /**
     * Transfer AuthorDTO to Author then call repository method create to insert data to storage.
     * After taking response from repository Transfer to AuthorDTO.
     * @param bean AuthorDTO with name and surname of author.
     * @return AuthorDTO with generated id.
     */
    @Override
    public AuthorDTO create(AuthorDTO bean) {
        return mapper.toDTO(repository.create(mapper.toBean(bean)));

    }

    /**
     * Before delete author take authorId of all news of this author. If author has news delete all his news.
     * then delete author.
     * @param authorId of author which try to delete
     * @return if author exist and was deleted return true, otherwise false.
     */
    @Override
    public boolean delete(long authorId) {
        List<Long> newsIds = takeAllNewsByAuthor(authorId);
        deleteAllNewsOfAuthor(newsIds);
        return repository.delete(authorId);
    }

    private List<Long> takeAllNewsByAuthor(long authorId) {
        return newsRepository.findNewsIdByAuthor(authorId);
    }

    private void deleteAllNewsOfAuthor(List<Long> newsIds){
        for (Long newsId : newsIds) {
            newsRepository.delete(newsId);
        }
    }

    @Override
    public AuthorDTO update(AuthorDTO bean) {
        return mapper.toDTO(repository.update(mapper.toBean(bean)));
    }


    @Override
    public AuthorDTO findById(long authorId) {
        return mapper.toDTO(repository.findBy(authorId));
    }
}
