package com.epam.lab.model;

public enum OrderBy {
    AUTHOR_NAME(Author_.NAME),
    AUTHOR_SURNAME(Author_.SURNAME),
    TITLE(News_.TITLE),
    CREATION_DATE(News_.CREATION_DATE),
    MODIFICATION_DATE(News_.MODIFICATION_DATE);


    private String columnName;

    OrderBy(String columnName) {
        this.columnName = columnName;
    }

    public String getColumn(){
        return columnName;
    }
}
