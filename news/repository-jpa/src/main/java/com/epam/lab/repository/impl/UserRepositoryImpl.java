package com.epam.lab.repository.impl;

import com.epam.lab.exception.UserNotFoundException;
import com.epam.lab.model.*;
import com.epam.lab.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import static com.epam.lab.model.Bean_.ID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User create(User bean) {
        entityManager.persist(bean);
        return bean;
    }

    @Override
    public boolean delete(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<User> criteriaDelete = criteriaBuilder.createCriteriaDelete(User.class);
        Root<User> root = criteriaDelete.from(User.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get(ID), id));
        int result = entityManager.createQuery(criteriaDelete).executeUpdate();
        return isDeleted(result);
    }

    private boolean isDeleted(int numberOfDeletedLines) {
        return numberOfDeletedLines != 0;
    }

    @Override
    public User update(User bean) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<User> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(User.class);
        Root<User> root = criteriaUpdate.from(User.class);

        criteriaUpdate.set(User_.NAME, bean.getName());
        criteriaUpdate.set(User_.SURNAME, bean.getSurname());
        criteriaUpdate.set(User_.LOGIN, bean.getLogin());
        criteriaUpdate.set(User_.PASSWORD, bean.getPassword());
        criteriaUpdate.where(criteriaBuilder.equal(root.get(ID), bean.getId()));
        int result = entityManager.createQuery(criteriaUpdate).executeUpdate();
        if (result == 0) {
            throw new UserNotFoundException();
        }

        //TODO think about update role
//        CriteriaUpdate<Roles> criteriaUpdateRoles = criteriaBuilder.createCriteriaUpdate(Roles.class);
//        Root<Roles> rolesRoot = criteriaUpdateRoles.from(Roles.class);
//        criteriaUpdate.set(rolesRoot.get(Roles_.ROLE), bean.getRole().getRole())
//                .where(criteriaBuilder.equal(rolesRoot.get(ID), bean.getId()));
//        result = entityManager.createQuery(criteriaUpdateRoles).executeUpdate();
//        System.out.println(result + "RESULT-2");
        return bean;
    }

    @Override
    public User findBy(long id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }
}
