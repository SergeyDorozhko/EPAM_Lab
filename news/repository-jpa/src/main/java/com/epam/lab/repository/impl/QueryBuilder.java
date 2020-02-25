package com.epam.lab.repository.impl;


import com.epam.lab.exception.ErrorOrderByException;
import com.epam.lab.model.*;
import jdk.internal.reflect.ConstantPool;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

final class QueryBuilder {
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<News> criteriaQuery;
    private Root<News> newsRoot;
    private Join<News, Author> newsAuthorJoin;
    private Join<News, Tag> newsTagJoin;

    private List<Predicate> predicates;

    QueryBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
        criteriaQuery = criteriaBuilder.createQuery(News.class);
        newsRoot = criteriaQuery.from(News.class);
        newsAuthorJoin = newsRoot.join(News_.AUTHOR, JoinType.LEFT);
        newsTagJoin = newsRoot.join(News_.TAGS, JoinType.LEFT);
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
        for (String tag : tags) {

            Subquery<String> subquery = criteriaQuery.subquery(String.class);
            Root<News> subRoot = subquery.from(News.class);
            Join<Tag, News> subTags = subRoot.join(News_.TAGS);

            subquery.select(subTags.get(Tag_.NAME));
            subquery.where(criteriaBuilder.equal(subRoot.get(News_.TAGS), subTags.get(Tag_.ID)));

            predicates.add(criteriaBuilder.equal(criteriaBuilder.any(subquery), tag));
        }


//        Path<String> tagPath = newsTagJoin.get(Tag_.NAME);
//        for (String tag : tags) {
//            predicates.add(criteriaBuilder.equal(tagPath, tag));
//
//        }

    }

    private void orderBy(Set<String> orderParams) {
        List<Order> orders = new ArrayList<>();
        for (String sortBy : orderParams) {
            OrderBy orderBy = getOrderBy(sortBy);
            if (orderBy == null) continue;
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

    private OrderBy getOrderBy(String sortBy) {
        OrderBy orderBy;
        try {
            orderBy = OrderBy.valueOf(sortBy.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
        return orderBy;
    }
}
