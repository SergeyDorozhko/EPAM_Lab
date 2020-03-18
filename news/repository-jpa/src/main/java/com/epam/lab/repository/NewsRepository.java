package com.epam.lab.repository;

import com.epam.lab.model.News;
import com.epam.lab.model.SearchCriteria;

import java.util.List;

public interface NewsRepository extends Repository<News> {
    void linkAuthorWithNews(long authorId, long newsId);

    Long findAuthorIdByNewsId(long newsId);

    List<Long> findNewsIdByAuthor(long authorId);

    long countAllNews();

    List<News> findAllNewsAndSortByQuery(SearchCriteria searchCriteria);

    long countAllNewsAndSortByQuery(SearchCriteria searchCriteria);

}