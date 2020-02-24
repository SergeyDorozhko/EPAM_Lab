package com.epam.lab.dto.mapper;

import com.epam.lab.dto.SearchCriteriaDTO;
import com.epam.lab.model.SearchCriteria;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchCriteriaMapper extends AbstractMapper<SearchCriteria,SearchCriteriaDTO> {

    @Autowired
    public SearchCriteriaMapper(ModelMapper modelMapper) {
        super(SearchCriteria.class, SearchCriteriaDTO.class, modelMapper);
    }
}
