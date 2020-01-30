package com.epam.lab.dto.Mapper;

import com.epam.lab.dto.TagDTO;
import com.epam.lab.model.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagMapper extends AbstractMapper<Tag, TagDTO> {
//    @Autowired
//    public TagMapper(Class<Tag> tag, Class<TagDTO> tagDTO, ModelMapper modelMapper) {
//        super(tag, tagDTO, modelMapper);
//    }

    @Autowired
    public TagMapper(ModelMapper modelMapper) {
        super(Tag.class, TagDTO.class, modelMapper);
    }
}
