package com.epam.lab.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class News extends Bean {
    private String title;
    private String short_text;
    private String full_text;
    private LocalDate creation_date;
    private LocalDate modification_date;

    private Author author;

    private List<Tag> listOfTags;

    public News(){
        super();
        listOfTags = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_text() {
        return short_text;
    }

    public void setShort_text(String short_text) {
        this.short_text = short_text;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public LocalDate getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDate creation_date) {
        this.creation_date = creation_date;
    }

    public LocalDate getModification_date() {
        return modification_date;
    }

    public void setModification_date(LocalDate modification_date) {
        this.modification_date = modification_date;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Tag> getListOfTags() {
        return listOfTags;
    }

    public void addTag(Tag tag) {
        listOfTags.add(tag);
    }
}
