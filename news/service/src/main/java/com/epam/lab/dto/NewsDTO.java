package com.epam.lab.dto;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NewsDTO newsDTO = (NewsDTO) o;

        if (title != null ? !title.equals(newsDTO.title) : newsDTO.title != null) return false;
        if (shortText != null ? !shortText.equals(newsDTO.shortText) : newsDTO.shortText != null) return false;
        if (fullText != null ? !fullText.equals(newsDTO.fullText) : newsDTO.fullText != null) return false;
        if (creationDate != null ? !creationDate.equals(newsDTO.creationDate) : newsDTO.creationDate != null)
            return false;
        if (modificationDate != null ? !modificationDate.equals(newsDTO.modificationDate) : newsDTO.modificationDate != null)
            return false;
        if (author != null ? !author.equals(newsDTO.author) : newsDTO.author != null) return false;
        return listOfTags != null ? listOfTags.equals(newsDTO.listOfTags) : newsDTO.listOfTags == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (shortText != null ? shortText.hashCode() : 0);
        result = 31 * result + (fullText != null ? fullText.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (modificationDate != null ? modificationDate.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (listOfTags != null ? listOfTags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewsDTO{" +
                "title='" + title + '\'' +
                ", shortText='" + shortText + '\'' +
                ", fullText='" + fullText + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", author=" + author +
                ", listOfTags=" + listOfTags +
                '}';
    }
}
