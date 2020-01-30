package com.epam.lab.dto;

import com.epam.lab.model.Author;
import com.epam.lab.model.Tag;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Component
public class NewsDTO extends AbstractDTO {
    private String title;
    private String short_text;
    private String full_text;
    private LocalDate creation_date;
    private LocalDate modification_date;

    private AuthorDTO author;

    private List<TagDTO> listOfTags;

    public NewsDTO(){
        super();
        listOfTags = new ArrayList<>();
    }

    public void setListOfTags(List<TagDTO> listOfTags) {
        this.listOfTags = listOfTags;
    }

    public NewsDTO(int id, String title, String short_text, String full_text, LocalDate creation_date, LocalDate modification_date, AuthorDTO author, List<TagDTO> listOfTags) {
        super(id);
        this.title = title;
        this.short_text = short_text;
        this.full_text = full_text;
        this.creation_date = creation_date;
        this.modification_date = modification_date;
        this.author = author;
        this.listOfTags = listOfTags;
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

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public List<TagDTO> getListOfTags() {
        return listOfTags;
    }

    public void addTag(TagDTO tag) {
        listOfTags.add(tag);
    }
}
