package com.epam.lab.repository;

import com.epam.lab.model.Tag;

public interface TagRepository extends InterfaceRepository<Tag> {

    Tag findBy(String name);
    Tag findBy(Tag tag);
}
