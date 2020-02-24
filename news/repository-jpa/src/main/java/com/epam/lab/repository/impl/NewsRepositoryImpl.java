package com.epam.lab.repository.impl;

import com.epam.lab.exception.AuthorNotFoundException;
import com.epam.lab.exception.ErrorOrderByException;
import com.epam.lab.exception.NewsWithoutAuthorException;
import com.epam.lab.model.*;
import com.epam.lab.repository.NewsRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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


//        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
//        Root<News> newsRoot = criteriaQuery.from(News.class);
//        Join<News, Author> newsAuthorJoin = newsRoot.join(News_.AUTHOR, JoinType.LEFT);
//        ListJoin<News, Tag> newsTagJoin = newsRoot.joinList(News_.TAGS, JoinType.LEFT);
//
//        criteriaQuery.select(newsRoot);
//
//        List<Predicate> predicates = new ArrayList<>();
//
//
//        criteriaQuery.groupBy(
//                newsRoot.get(News_.ID),
//                newsRoot.get(News_.AUTHOR).get(Author_.ID),
//                newsAuthorJoin.get(Author_.NAME),
//                newsAuthorJoin.get(Author_.SURNAME));
//
//        if (searchCriteria.getAuthorName() != null) {
//            predicates.add(criteriaBuilder.equal(newsAuthorJoin.get(Author_.NAME), searchCriteria.getAuthorName()));
//        }
//        if (searchCriteria.getAuthorSurname() != null) {
//            predicates.add(criteriaBuilder.equal(newsAuthorJoin.get(Author_.SURNAME), searchCriteria.getAuthorSurname()));
//        }
//        if (!searchCriteria.getTags().isEmpty()) {
//            Path<String> tagPath = newsTagJoin.get(Tag_.NAME);
//            for (String tag : searchCriteria.getTags()) {
//                predicates.add(criteriaBuilder.equal(tagPath, tag));
//            }
//
//        }
//
//        criteriaQuery.where(predicates.toArray(new Predicate[0]));
//
//        List<Order> orders = new ArrayList<>();
//        for (String sortBy : searchCriteria.getOrderByParameter()) {
//
//            OrderBy orderBy = OrderBy.valueOf(sortBy.toUpperCase());
//            switch (orderBy) {
//                case TITLE:
//                case CREATION_DATE:
//                case MODIFICATION_DATE:
//                    orders.add(criteriaBuilder.asc(newsRoot.get(orderBy.getColumn())));
//                    break;
//                case AUTHOR_NAME:
//                case AUTHOR_SURNAME:
//                    orders.add(criteriaBuilder.asc(newsAuthorJoin.get(orderBy.getColumn())));
//                    break;
//                default:
//                    throw new ErrorOrderByException();
//            }
//        }
//        criteriaQuery.orderBy(orders);
//
//        return entityManager.createQuery(criteriaQuery).getResultList();
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
        return entityManager.merge(bean);
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
