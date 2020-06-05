package com.epam.lab.repository;

import com.epam.lab.model.News;

import java.util.List;

public interface InitNewsRepository extends Repository<News> {
    List<String> findAllFiles(String rootPath);
    void deleteFile(String path);
    void restoreFile(String pathFrom, String pathTo);
    List<News> readCatalog(String path);
}
