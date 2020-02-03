package com.epam.lab.repository;

import com.epam.lab.model.News;
import com.epam.lab.model.Tag;

import java.util.List;
import java.util.Set;

public interface TagRepository extends InterfaceRepository<Tag> {

    Tag findBy(String name);
    Tag findBy(Tag tag);
    List<Tag> findBy(News news);
    void linkTagWithNews(long tagId, long newsId);
    int deleteTagNewsLinks(long newsId);
}
