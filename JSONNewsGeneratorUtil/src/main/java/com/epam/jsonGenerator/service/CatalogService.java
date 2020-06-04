package com.epam.jsonGenerator.service;

public interface CatalogService {
    void createCatalogs(int subfoldersCount, String basePath);
    void dropAll(String basePath);
}
