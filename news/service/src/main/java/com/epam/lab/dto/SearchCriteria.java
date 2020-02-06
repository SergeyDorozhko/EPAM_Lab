package com.epam.lab.dto;

import java.util.HashSet;
import java.util.Set;

public class SearchCriteria {
    private String name;
    private String surname;
    private Set<String> tagsList;
    private Set<String> orderByParameter;
    private boolean desc;

    public SearchCriteria() {
        this.tagsList = new HashSet<>();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public Set<String> getTagsList() {
        return tagsList;
    }
    public void setTagsList(Set<String> tagsList) {
        this.tagsList = tagsList;
    }
    public Set<String> getOrderByParameter() {
        return orderByParameter;
    }
    public void setOrderByParameter(Set<String> orderByParameter) {
        this.orderByParameter = orderByParameter;
    }
    public boolean isAces() {
        return desc;
    }
    public void setAces(boolean aces) {
        this.desc = aces;
    }
    public String accept() {
        StringBuilder builder = new StringBuilder();
        builder.append("WHERE (1=1) ");
        if (name != null && !name.isEmpty()) {
            builder.append(" AND (author.name =").append(name).append(") ");
        }
        if (surname != null && !surname.isEmpty()) {
            builder.append(" AND (author.surname =").append(surname).append(") ");
        }
        tagsList.forEach(c -> builder.append(" AND (").append(c).append(" = ANY(tag_names)) "));
        if (orderByParameter != null && !orderByParameter.isEmpty()) {
            builder.append(" ORDER BY ").append(orderByParameter);
        }
        if (desc) {
            builder.append(" DESC");
        }
        return builder.append(";").toString();
    }
}
