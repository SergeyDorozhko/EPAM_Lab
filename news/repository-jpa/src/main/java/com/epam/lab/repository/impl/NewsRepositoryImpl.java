package com.epam.lab.repository.impl;

import com.epam.lab.exception.NewsNotFoundException;
import com.epam.lab.exception.NewsNotUpdatedException;
import com.epam.lab.exception.NewsWithoutAuthorException;
import com.epam.lab.model.*;
import com.epam.lab.repository.NewsRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<News> root = criteriaQuery.from(News.class);

        Join<News, Author> authorNewsJoin = root.join("author");
        criteriaQuery.select(authorNewsJoin.get("id")).where(criteriaBuilder.equal(root.get("id"), newsId));
        Long resultId = null;
        try {
            resultId = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException ex) {
            throw new NewsWithoutAuthorException();
        }
        return resultId;
    }

    @Override
    public List<Long> findNewsIdByAuthor(long authorId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<News> root = criteriaQuery.from(News.class);

        Join<News, Author> authorNewsJoin = root.join(News_.AUTHOR);
        criteriaQuery.select(root.get(News_.ID)).where(criteriaBuilder.equal(authorNewsJoin.get(Author_.ID), authorId));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public long countAllNews() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<News> root = criteriaQuery.from(News.class);

        criteriaQuery.select(criteriaBuilder.count(root.get(News_.ID)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<News> findAllNewsAndSortByQuery(SearchCriteria searchCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        QueryBuilder queryBuilder = new QueryBuilder(criteriaBuilder);
        return entityManager.createQuery(queryBuilder.buildCriteriaQuery(searchCriteria)).getResultList();

    }

    @Override
    public News create(News bean) {
        entityManager.persist(bean);
        return bean;
    }

    @Override
    public boolean delete(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<News> criteriaDelete = criteriaBuilder.createCriteriaDelete(News.class);
        Root<News> root = criteriaDelete.from(News.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));
        int result = entityManager.createQuery(criteriaDelete).executeUpdate();
        return isDeleted(result);
    }

    private boolean isDeleted(int numberOfDeletedLines) {
        return numberOfDeletedLines != 0;
    }

    @Override
    public News update(News bean) {
        try {
            return entityManager.merge(bean);
        } catch (PersistenceException e) {
            throw new NewsNotUpdatedException();
        }
    }


    @Override
    public News findBy(long id) {
        News news = entityManager.find(News.class, id);
        newsFounded(news);
        return news;
    }

    private void newsFounded(News news){
        if(news == null) {
            throw new NewsNotFoundException();
        }
    }
}
