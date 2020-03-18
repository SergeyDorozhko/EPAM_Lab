package com.epam.lab.dto;

import java.util.List;

public class AllNewsByQuery {
    private List<NewsDTO> items;
    private long totalCount;

    public List<NewsDTO> getItems() {
        return items;
    }

    public void setItems(List<NewsDTO> items) {
        this.items = items;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
