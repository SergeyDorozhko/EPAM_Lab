package com.epam.jsonGenerator;

import com.epam.jsonGenerator.service.ServiceFactory;

import java.util.Date;
import java.util.ResourceBundle;

public class MainTest {

    private static final String PROPERTY_FILE = "app";
    private static final String ROOT_CATALOG = "rootCatalog";
    private static final String SUBFOLDERS_COUNT = "subfoldersCount";
    private static final String FILES_COUNT = "filesCount";

    public static void main(String[] args) {
        ResourceBundle rb = ResourceBundle.getBundle(PROPERTY_FILE);
        String path = rb.getString(ROOT_CATALOG);
        int subfoldersCount = Integer.valueOf(rb.getString(SUBFOLDERS_COUNT));
        int filesCount = Integer.valueOf(rb.getString(FILES_COUNT));


        ServiceFactory.getInstance().getCatalogService().dropAll(path);

        ServiceFactory.getInstance().getCatalogService().createCatalogs(subfoldersCount, path);

        ServiceFactory.getInstance().getNewsService().generatorFile(path);
    }



}
