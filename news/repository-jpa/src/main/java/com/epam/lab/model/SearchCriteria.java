package com.epam.lab.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class SearchCriteria extends Bean{
    private String authorName;
    private String authorSurname;
    private Set<String> tags;
    private Set<String> orderByParameter;
    private boolean desc;

    public SearchCriteria() {
        this.tags = new LinkedHashSet<>();
        this.orderByParameter = new LinkedHashSet<>();
    }

    public SearchCriteria(String authorName,
                          String authorSurname,
                          Set<String> tags,
                          Set<String> orderByParameter,
                          boolean desc) {
        this.authorName = authorName;
        this.authorSurname = authorSurname;
        this.tags = tags;
        this.orderByParameter = orderByParameter;
        this.desc = desc;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Set<String> getOrderByParameter() {
        return orderByParameter;
    }

    public void setOrderByParameter(Set<String> orderByParameter) {
        this.orderByParameter = orderByParameter;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SearchCriteria that = (SearchCriteria) o;
        return desc == that.desc &&
                Objects.equals(authorName, that.authorName) &&
                Objects.equals(authorSurname, that.authorSurname) &&
                Objects.equals(tags, that.tags) &&
                Objects.equals(orderByParameter, that.orderByParameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), authorName, authorSurname, tags, orderByParameter, desc);
    }
}
