package com.epam.lab.service;

import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.SearchCriteria;

import java.util.List;

public interface NewsService extends InterfaceService<NewsDTO> {
    long countAllNews();
    List<NewsDTO> findAllNewsByQuery(SearchCriteria searchCriteria);
}
