package com.epam.lab.service.impl;

import com.epam.lab.dto.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

final class QueryBuilder {

    private static final String START_QUERY = "WHERE (1=1) ";
    private static final String AUTHOR_NAME_COLUMN = " AND (author_name = '";
    private static final String AUTHOR_SURNAME_COLUMN = " AND (author_surname = '";
    private static final String CLOSE_BRACKETS = "') ";
    private static final String OPEN_BRACKETS = " AND ('";
    private static final String TAG_NAMES_COLUMN = "' = ANY(tag_names)) ";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String COMMA_SEPARATOR = ", ";
    private static final String SORT_ORDER_CONDITION = " DESC";
    private static final String SEMICOLON = ";";
    private StringBuilder query;

    QueryBuilder() {
        query = new StringBuilder(START_QUERY);
    }

    QueryBuilder setAuthorName(String authorName) {
        if (isParameterExist(authorName)) {
            query.append(AUTHOR_NAME_COLUMN).append(authorName).append(CLOSE_BRACKETS);
        }
        return this;
    }

    private boolean isParameterExist(String value) {
        return value != null && !value.isEmpty();
    }

    QueryBuilder setAuthorSurname(String authorSurname) {
        if (isParameterExist(authorSurname)) {
            query.append(AUTHOR_SURNAME_COLUMN).append(authorSurname).append(CLOSE_BRACKETS);
        }
        return this;
    }

    QueryBuilder setTags(Set<String> tags) {
        tags.forEach(c -> query.append(OPEN_BRACKETS).append(c).append(TAG_NAMES_COLUMN));
        return this;
    }

    QueryBuilder setSort(SearchCriteria orderParameters) {
        if (hasNoParametersToSort(orderParameters)) {
            return this;
        }
        setSortByParameters(orderParameters);
        setSortOrder(orderParameters);
        return this;
    }

    private boolean hasNoParametersToSort(SearchCriteria orderParameters) {
        return orderParameters.getOrderByParameter().isEmpty();
    }

    private void setSortByParameters(SearchCriteria orderParameters) {
        query.append(ORDER_BY);
        List<String> orderSet = new ArrayList<>(orderParameters.getOrderByParameter());
        for (int i = 0; i < orderSet.size(); i++) {
            if (i > 0) {
                query.append(COMMA_SEPARATOR);
            }
            query.append(orderSet.get(i));
        }
    }

    private void setSortOrder(SearchCriteria orderParameters) {
        if (orderParameters.isDesc()) {
            query.append(SORT_ORDER_CONDITION);
        }
    }

    String buildQuery() {
        return query.append(SEMICOLON).toString();
    }
}
