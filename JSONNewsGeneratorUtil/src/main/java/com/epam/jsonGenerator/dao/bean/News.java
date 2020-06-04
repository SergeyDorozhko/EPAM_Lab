package com.epam.jsonGenerator.dao.bean;

import com.epam.jsonGenerator.dao.bean.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class News extends Bean {

    private String title;
    private String shortText;
    private String fullText;
    private LocalDate creationDate;
    private LocalDate modificationDate;

    private Author author;

    private List<Tag> tags;

    public News(){
        super();
        tags = new ArrayList<>();
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        if (!super.equals(o)) return false;
        News news = (News) o;
        return Objects.equals(title, news.title) &&
                Objects.equals(shortText, news.shortText) &&
                Objects.equals(fullText, news.fullText) &&
                Objects.equals(creationDate, news.creationDate) &&
                Objects.equals(modificationDate, news.modificationDate) &&
                Objects.equals(author, news.author) &&
                Objects.equals(tags, news.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                title, shortText, fullText, creationDate,
                modificationDate, author, tags);
    }

    @Override
    public String toString() {
        return "News{" +
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
