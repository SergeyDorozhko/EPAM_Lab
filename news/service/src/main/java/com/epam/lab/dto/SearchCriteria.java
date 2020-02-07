package com.epam.lab.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public class SearchCriteria {
    private String authorName;
    private String authorSurname;
    private Set<String> tagsList;
    private Set<String> orderByParameter;
    private boolean desc;

    public SearchCriteria() {
        this.tagsList = new LinkedHashSet<>();
        this.orderByParameter = new LinkedHashSet<>();
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

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }
}
