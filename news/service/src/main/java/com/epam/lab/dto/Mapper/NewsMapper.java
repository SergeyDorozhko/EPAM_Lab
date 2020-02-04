package com.epam.lab.dto.Mapper;

import com.epam.lab.dto.NewsDTO;
import com.epam.lab.model.News;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper extends AbstractMapper<News, NewsDTO> {


    @Autowired
    public NewsMapper (ModelMapper modelMapper){
        super(News.class, NewsDTO.class, modelMapper);
    }
}
