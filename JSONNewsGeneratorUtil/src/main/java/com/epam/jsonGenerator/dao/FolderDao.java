package com.epam.jsonGenerator.dao;

public interface FolderDao {
    void create(int subFolders, String basePath);
    void clearBaseDirectory(String basePath);
}
