package com.epam.lab.repository.impl;

import com.epam.lab.model.News;
import com.epam.lab.repository.NewsRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class NewsRepositoryImpl implements NewsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void linkAuthorWithNews(long authorId, long newsId) {

    }

    @Override
    public Long findAuthorIdByNewsId(long newsId) {
        return null;
    }

    @Override
    public List<Long> findNewsIdByAuthor(long authorId) {
        return null;
    }

    @Override
    public long countAllNews() {
        return 0;
    }

    @Override
    public List<News> findAllNewsAndSortByQuery(String query) {
        return null;
    }

    @Override
    public News create(News bean) {
        entityManager.persist(bean);
        return bean;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public News update(News bean) {
        return null;
    }

    @Override
    public News findBy(long id) {
        return entityManager.find(News.class, id);
    }
}
