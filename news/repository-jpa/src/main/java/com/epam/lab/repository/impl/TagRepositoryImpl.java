package com.epam.lab.repository.impl;

import com.epam.lab.exception.TagNotFoundException;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.model.Tag_;
import com.epam.lab.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

import static com.epam.lab.model.Bean_.ID;

@Repository
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Tag findBy(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = cb.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root).where(cb.equal(root.get(Tag_.NAME), name));
        Tag tag = null;
        try {
            tag = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException ex) {
            throw new TagNotFoundException();
        }
        return tag;
    }

    @Override
    public Tag findBy(Tag tag) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(ID), tag.getId()), criteriaBuilder.equal(root.get(Tag_.NAME), tag.getName()));

        Tag foundTag = null;
        try {
            foundTag = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException ex) {
            throw new TagNotFoundException();
        }

        return foundTag;
    }

    @Override
    public List<Tag> findBy(News news) {
        return news.getTags();
    }

    /*this method exist only for realise switch between JDBC and JPA repositorty.
     * in JPA repository no need of realisation.
     */
    @Override
    public void linkTagWithNews(long tagId, long newsId) {

    }

    /*this method exist only for realise switch between JDBC and JPA repositorty.
     * in JPA repository no need of realisation.
     */
    @Override
    public void deleteTagNewsLinks(long newsId) {

    }

    @Override
    public Tag create(Tag bean) {
        entityManager.persist(bean);
        return bean;
    }

    @Override
    public boolean delete(long id) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Tag> criteriaDelete = criteriaBuilder.createCriteriaDelete(Tag.class);
        Root<Tag> root = criteriaDelete.from(Tag.class);

        criteriaDelete.where(criteriaBuilder.equal(root.get(ID), id));

        int result = entityManager.createQuery(criteriaDelete).executeUpdate();

        return isDeleted(result);
    }

    private boolean isDeleted(int numberOfDeletedLines) {
        return numberOfDeletedLines != 0;
    }


    @Override
    public Tag update(Tag bean) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Tag> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Tag.class);
        Root<Tag> root = criteriaUpdate.from(Tag.class);
        criteriaUpdate.set(root.get(Tag_.NAME), bean.getName());
        criteriaUpdate.where(criteriaBuilder.equal(root.get(ID), bean.getId()));

        int result = entityManager.createQuery(criteriaUpdate).executeUpdate();
        isUpdated(result);
        return bean;
    }

    private void isUpdated(int numberOfUpdatedLines) {
        if (numberOfUpdatedLines == 0) {
            throw new TagNotFoundException();
        }
    }


    @Override
    public Tag findBy(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag == null) {
            throw new TagNotFoundException();
        }
        return tag;
    }
}
