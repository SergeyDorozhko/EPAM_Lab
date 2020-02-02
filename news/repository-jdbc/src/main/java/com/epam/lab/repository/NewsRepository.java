package com.epam.lab.repository;

import com.epam.lab.model.News;

public interface NewsRepository extends InterfaceRepository <News> {
    void linkAuthorWithNews(long authorId, long newsId);

}
