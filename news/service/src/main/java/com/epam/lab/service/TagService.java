package com.epam.lab.service;

import com.epam.lab.dto.TagDTO;

import java.util.List;

public interface TagService extends Service<TagDTO> {
    List<TagDTO> findAll();
}
