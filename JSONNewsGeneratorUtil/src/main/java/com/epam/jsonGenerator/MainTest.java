package com.epam.jsonGenerator;

import com.epam.jsonGenerator.service.ServiceFactory;

import java.util.Date;
import java.util.ResourceBundle;

public class MainTest {

    public static void main(String[] args) {
        long now = new Date().getTime();
        ResourceBundle rb = ResourceBundle.getBundle("app");
        String path = rb.getString("rootCatalog");
        int subfoldersCount = Integer.valueOf(rb.getString("subfoldersCount"));
        long taskTime = Long.valueOf(rb.getString("testTime"));

        ServiceFactory.getInstance().getCatalogService().dropAll(path);
        ServiceFactory.getInstance().getCatalogService().createCatalogs(subfoldersCount, path);

        ServiceFactory.getInstance().getNewsService().generator(path, taskTime);
        System.out.println("FINISH in : " + ((double)(new Date().getTime() - now)) / 1000d + "s.");
    }



}
