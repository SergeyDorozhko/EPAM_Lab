package com.epam.lab.dto;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;


@Component
public class NewsDTO extends AbstractDTO {
    private String title;
    private String shortText;
    private String fullText;
    private LocalDate creationDate;
    private LocalDate modificationDate;

    private AuthorDTO author;

    private List<TagDTO> listOfTags;

    public NewsDTO(){
        super();
        listOfTags = new ArrayList<>();
    }

    public NewsDTO(int id, String title, String shortText, String fullText, LocalDate creationDate, LocalDate modificationDate, AuthorDTO author, List<TagDTO> listOfTags) {
        super(id);
        this.title = title;
        this.shortText = shortText;
        this.fullText = fullText;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.author = author;
        this.listOfTags = listOfTags;
    }

    public void setListOfTags(List<TagDTO> listOfTags) {
        this.listOfTags = listOfTags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsDTO)) return false;
        if (!super.equals(o)) return false;
        NewsDTO newsDTO = (NewsDTO) o;
        return Objects.equals(title, newsDTO.title) &&
                Objects.equals(shortText, newsDTO.shortText) &&
                Objects.equals(fullText, newsDTO.fullText) &&
                Objects.equals(creationDate, newsDTO.creationDate) &&
                Objects.equals(modificationDate, newsDTO.modificationDate) &&
                Objects.equals(author, newsDTO.author) &&
                Objects.equals(listOfTags, newsDTO.listOfTags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, shortText, fullText, creationDate, modificationDate, author, listOfTags);
    }
}
