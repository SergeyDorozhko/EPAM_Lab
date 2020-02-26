package com.epam.lab.dto;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

public class SearchCriteriaDTO extends AuthorDTO {
    @Pattern(regexp = "(^[A-Za-zА-Яа-я0-9-]+$)", message = "Name must consist of letters or '-'.")
    @Size(min = 1, max = 30, message = "Name size must be between 1 and 30.")
    private String authorName;
    @Pattern(regexp = "(^[A-Za-zА-Яа-я0-9-]+$)", message = "Surname must consist of letters or '-'.")
    @Size(min = 1, max = 30, message = "Surname size must be between 1 and 30.")
    private String authorSurname;

    private Set<String> tags;

    private Set<String> orderByParameter;
    private boolean desc;

    public SearchCriteriaDTO() {
        this.tags = new LinkedHashSet<>();
        this.orderByParameter = new LinkedHashSet<>();
    }

    public SearchCriteriaDTO(String authorName,
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
