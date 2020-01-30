package com.epam.lab.dto.Mapper;

import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.model.Author;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper extends AbstractMapper<Author, AuthorDTO> {

//    @Autowired
//    public AuthorMapper( Class<Author> author,Class <AuthorDTO> authorDTO, ModelMapper modelMapper) {
//        super(author, authorDTO, modelMapper);
//    }


        @Autowired
    public AuthorMapper(ModelMapper modelMapper) {
        super(Author.class, AuthorDTO.class, modelMapper);
    }
}
