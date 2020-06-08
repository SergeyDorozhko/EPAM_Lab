package com.epam.jsonGenerator.dao;


import com.epam.jsonGenerator.dao.bean.Bean;

import java.util.List;

public interface JsonDao<T extends Bean> {
    boolean write(List<T> t, String path, int fileType);
}
