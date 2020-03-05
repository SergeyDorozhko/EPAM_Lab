package com.epam.lab.service;

import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.SearchCriteriaDTO;

import java.util.List;

public interface NewsService extends Service<NewsDTO> {
    long countAllNews();
    List<NewsDTO> findAllNewsByQuery(SearchCriteriaDTO searchCriteriaDTO);
}
