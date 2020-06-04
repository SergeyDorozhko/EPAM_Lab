package com.epam.jsonGenerator.service;

import com.epam.jsonGenerator.service.impl.CatalogServiceImpl;
import com.epam.jsonGenerator.service.impl.NewsServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private CatalogService catalogService = new CatalogServiceImpl();

    private NewsService newsService = new NewsServiceImpl();
    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public CatalogService getCatalogService() {
        return catalogService;
    }

    public NewsService getNewsService() {
        return newsService;
    }
}
