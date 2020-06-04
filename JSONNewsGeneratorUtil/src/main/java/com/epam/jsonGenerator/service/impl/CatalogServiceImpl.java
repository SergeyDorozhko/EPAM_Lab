package com.epam.jsonGenerator.service.impl;

import com.epam.jsonGenerator.dao.FactoryDao;
import com.epam.jsonGenerator.dao.FolderDao;
import com.epam.jsonGenerator.dao.impl.FolderDaoImpl;
import com.epam.jsonGenerator.service.CatalogService;

public class CatalogServiceImpl implements CatalogService {
    @Override
    public void createCatalogs(int subfoldersCount, String basePath) {
        FactoryDao.getInstance().getFolderDao().create(subfoldersCount, basePath);
    }

    @Override
    public void dropAll(String basePath) {
        FactoryDao.getInstance().getFolderDao().clearBaseDirectory(basePath);
    }
}
