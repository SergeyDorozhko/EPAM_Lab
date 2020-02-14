package com.epam.lab.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public class SearchCriteria {
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
}
