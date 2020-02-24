package com.epam.lab.repository.impl;


import com.epam.lab.exception.ErrorOrderByException;
import com.epam.lab.model.*;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

final class QueryBuilder {
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<News> criteriaQuery;
    private Root<News> newsRoot;
    private Join<News, Author> newsAuthorJoin;
    private ListJoin<News, Tag> newsTagJoin;

    private List<Predicate> predicates;

    QueryBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
        criteriaQuery = criteriaBuilder.createQuery(News.class);
        newsRoot = criteriaQuery.from(News.class);
        newsAuthorJoin = newsRoot.join(News_.AUTHOR, JoinType.LEFT);
        newsTagJoin = newsRoot.joinList(News_.TAGS, JoinType.LEFT);
        predicates = new ArrayList<>();
    }

    public CriteriaQuery<News> buildCriteriaQuery(SearchCriteria searchCriteria) {

        criteriaQuery.select(newsRoot);
        searchByAuthorName(searchCriteria.getAuthorName());
        searchByAuthorSurname(searchCriteria.getAuthorSurname());
        searchByTags(searchCriteria.getTags());

        criteriaQuery.groupBy(
                newsRoot.get(News_.ID),
                newsRoot.get(News_.AUTHOR).get(Author_.ID),
                newsAuthorJoin.get(Author_.NAME),
                newsAuthorJoin.get(Author_.SURNAME));

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        orderBy(searchCriteria.getOrderByParameter());
        return criteriaQuery;
    }

    private void searchByAuthorName(String authorName) {
        if (authorName != null) {
            predicates.add(criteriaBuilder.equal(newsAuthorJoin.get(Author_.NAME), authorName));
        }
    }

    private void searchByAuthorSurname(String authorSurname) {
        if (authorSurname != null) {
            predicates.add(criteriaBuilder.equal(newsAuthorJoin.get(Author_.SURNAME), authorSurname));
        }
    }

    private void searchByTags(Set<String> tags) {
        Path<String> tagPath = newsTagJoin.get(Tag_.NAME);
        for (String tag : tags) {
            predicates.add(criteriaBuilder.equal(tagPath, tag));
        }
    }

    private void orderBy(Set<String> orderParams){
        List<Order> orders = new ArrayList<>();
        for (String sortBy : orderParams) {

            OrderBy orderBy = OrderBy.valueOf(sortBy.toUpperCase());
            switch (orderBy) {
                case TITLE:
                case CREATION_DATE:
                case MODIFICATION_DATE:
                    orders.add(criteriaBuilder.asc(newsRoot.get(orderBy.getColumn())));
                    break;
                case AUTHOR_NAME:
                case AUTHOR_SURNAME:
                    orders.add(criteriaBuilder.asc(newsAuthorJoin.get(orderBy.getColumn())));
                    break;
                default:
                    throw new ErrorOrderByException();
            }
        }
        criteriaQuery.orderBy(orders);
    }
}
