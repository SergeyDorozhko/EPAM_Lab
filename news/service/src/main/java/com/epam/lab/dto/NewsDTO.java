package com.epam.lab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class NewsDTO extends AbstractDTO {
    private String title;
    private String shortText;
    private String fullText;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate modificationDate;

    private AuthorDTO author;

    private List<TagDTO> tags;

    public NewsDTO() {
        super();
        tags = new ArrayList<>();
    }

    public NewsDTO(int id, String title, String shortText, String fullText, LocalDate creationDate, LocalDate modificationDate,
                   AuthorDTO author, List<TagDTO> tags) {
        super(id);
        this.title = title;
        this.shortText = shortText;
        this.fullText = fullText;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.author = author;
        this.tags = tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
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

    public List<TagDTO> getTags() {
        return tags;
    }

    public void addTag(TagDTO tag) {
        tags.add(tag);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NewsDTO newsDTO = (NewsDTO) o;
        return Objects.equals(title, newsDTO.title) &&
                Objects.equals(shortText, newsDTO.shortText) &&
                Objects.equals(fullText, newsDTO.fullText) &&
                Objects.equals(creationDate, newsDTO.creationDate) &&
                Objects.equals(modificationDate, newsDTO.modificationDate) &&
                Objects.equals(author, newsDTO.author) &&
                Objects.equals(tags, newsDTO.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                title, shortText, fullText, creationDate, modificationDate, author, tags);
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
                ", listOfTags=" + tags +
                '}';
    }
}
