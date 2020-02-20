package com.epam.lab.repository.impl;

import com.epam.lab.exception.AuthorNotFoundException;
import com.epam.lab.exception.NewsWithoutAuthorException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.NewsRepository;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDate;
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

        Join<News, Author> authorNewsJoin = root.join("author");
        criteriaQuery.select(root.get("id")).where(criteriaBuilder.equal(authorNewsJoin.get("id"), authorId));

        return entityManager.createQuery(criteriaQuery).getResultList();
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

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<News> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(News.class);
        Root<News> root = criteriaUpdate.from(News.class);

//        Join<News, Author> newsAuthorJoin = root.join("author");
//        ListJoin<News, Tag> newsTagJoin = root.joinList("tags");


        criteriaUpdate.set(root.get("title"), bean.getTitle());
        criteriaUpdate.set(root.get("shortText"), bean.getShortText());
        criteriaUpdate.set(root.get("fullText"), bean.getFullText());
        criteriaUpdate.set(root.get("modificationDate"), bean.getModificationDate());
        criteriaUpdate.set(root.get("author"), bean.getAuthor());


//        for(Tag tag : bean.getTags()) {
//            criteriaUpdate.set(root.get("tags"), tag.getId());
//        }

       // criteriaUpdate.(root.get("tags"), bean.getTags());


        int result = entityManager.createQuery(criteriaUpdate).executeUpdate();

        isUpdated(result);


//        bean.setCreationDate(LocalDate.now());
//        return entityManager.merge(bean);
        return bean;
    }

    private void isUpdated(int numberOfUpdatedLines) {
        if (numberOfUpdatedLines == 0) {
            throw new AuthorNotFoundException();
        }
    }

    @Override
    public News findBy(long id) {
        return entityManager.find(News.class, id);
    }
}
