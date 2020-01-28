package com.epam.springstart;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CacheFileEventLogger extends FileEventLogger {



    private int cacheSize;
    private List<Event> cache;

    public CacheFileEventLogger(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public CacheFileEventLogger(String fileName, int cacheSize) {
        super(fileName);
        this.cacheSize = cacheSize;
        cache = new ArrayList<>();
    }

    @Override
    public void logEvent(Event event) {

        cache.add(event);
        if (cacheSize == cache.size()) {
            writeFromCache();
            cache.clear();
        }

    }

    private void writeFromCache() {
        for (Event eventCache : cache) {
            eventCache.setMsg(eventCache.getMsg() + " CACHE+++++++++n");
            super.logEvent(eventCache);
            System.out.println("\nwrite from cache\n");
        }

    }



    public void destroy(){
        System.out.println("\ndestroy is empty");

        if(!cache.isEmpty()) {
            writeFromCache();
            System.out.println("\ndestroy");
        }
    }


}
