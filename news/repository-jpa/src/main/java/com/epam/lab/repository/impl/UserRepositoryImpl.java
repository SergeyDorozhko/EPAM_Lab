package com.epam.lab.repository.impl;

import com.epam.lab.exception.UserNotFoundException;
import com.epam.lab.model.*;
import com.epam.lab.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public User singIn(User user) {
        Object[] userData = findUserByLoginPWD(user);
        return createUserFromData(userData);
    }


    private Object[] findUserByLoginPWD(User user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<User> root = criteriaQuery.from(User.class);
        List<Predicate> predicate = new ArrayList<>();
        predicate.add(criteriaBuilder.equal(root.get(User_.LOGIN), user.getLogin()));
        predicate.add(criteriaBuilder.equal(root.get(User_.PASSWORD), user.getPassword()));
        criteriaQuery.select(
                criteriaBuilder.array(root.get(ID),
                        root.get(User_.NAME),
                        root.get(User_.SURNAME),
                        root.get(User_.LOGIN),
                        root.get(User_.ROLE).get(Roles_.ROLE)));
        criteriaQuery.where(predicate.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    private User createUserFromData(Object[] data) {
        User authorizedUser = new User();
        int counter = 0;
        authorizedUser.setId((Long) data[counter++]);
        authorizedUser.setName((String) data[counter++]);
        authorizedUser.setSurname((String) data[counter++]);
        authorizedUser.setLogin((String) data[counter++]);
        Roles role = new Roles();
        role.setRole((Role) data[counter]);
        authorizedUser.setRole(role);
        return authorizedUser;
    }
}
