package com.epam.lab.service;

import com.epam.lab.dto.AllNewsByQuery;
import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.SearchCriteriaDTO;
import com.epam.lab.model.News;

import java.util.List;

public interface NewsService extends Service<NewsDTO> {
    long countAllNews();
    AllNewsByQuery findAllNewsByQuery(SearchCriteriaDTO searchCriteriaDTO);
    void batchCreate(List<NewsDTO> news);
}
