package com.epam.jsonGenerator.dao;

import com.epam.jsonGenerator.dao.impl.FolderDaoImpl;
import com.epam.jsonGenerator.dao.impl.NewsJsonDaoImpl;

public final class FactoryDao {
    private final static FactoryDao instance = new FactoryDao();

    private FolderDao folderDao = new FolderDaoImpl();
    private NewsJsonDao newsJsonDao = new NewsJsonDaoImpl();

    private FactoryDao() {
    }


    public static FactoryDao getInstance() {
        return instance;
    }

    public FolderDao getFolderDao() {
        return folderDao;
    }

    public NewsJsonDao getNewsJsonDao() {
        return newsJsonDao;
    }


}
