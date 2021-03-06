package com.epam.lab.repository.impl;

import com.epam.lab.exception.AuthorNotFoundException;
import com.epam.lab.model.Author;
import com.epam.lab.model.Author_;
import com.epam.lab.repository.AuthorRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.*;

import static com.epam.lab.model.Bean_.ID;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Author findBy(Author author) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> query = criteriaBuilder.createQuery(Author.class);
        Root<Author> root = query.from(Author.class);
        query.select(root)
                .where(criteriaBuilder.equal(root.get(ID), author.getId()),
                        criteriaBuilder.equal(root.get(Author_.NAME), author.getName()),
                        criteriaBuilder.equal(root.get(Author_.SURNAME), author.getSurname()));

        try {
            entityManager.createQuery(query).getSingleResult();
        } catch (PersistenceException e) {
            throw new AuthorNotFoundException();
        }
        return author;
    }

    @Override
    public Author create(Author bean) {
        entityManager.persist(bean);
        return bean;
    }

    @Override
    public boolean delete(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Author> criteriaDelete = criteriaBuilder.createCriteriaDelete(Author.class);
        Root<Author> root = criteriaDelete.from(Author.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get(ID), id));
        int result = entityManager.createQuery(criteriaDelete).executeUpdate();
        return isDeleted(result);
    }

    private boolean isDeleted(int numberOfDeletedLines) {
        return numberOfDeletedLines != 0;
    }

    @Override
    public Author update(Author bean) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Author> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Author.class);
        Root<Author> root = criteriaUpdate.from(Author.class);
        criteriaUpdate.set(root.get(Author_.NAME), bean.getName());
        criteriaUpdate.set(root.get(Author_.SURNAME), bean.getSurname());
        criteriaUpdate.where(criteriaBuilder.equal(root.get(ID), bean.getId()));
        int result = entityManager.createQuery(criteriaUpdate).executeUpdate();
        isUpdated(result);
        return bean;
    }

    private void isUpdated(int numberOfUpdatedLines) {
        if (numberOfUpdatedLines == 0) {
            throw new AuthorNotFoundException();
        }
    }


    @Override
    public Author findBy(long id) {
        Author author = entityManager.find(Author.class, id);
        if (author == null) {
            throw new AuthorNotFoundException();
        }
        return author;
    }
}
