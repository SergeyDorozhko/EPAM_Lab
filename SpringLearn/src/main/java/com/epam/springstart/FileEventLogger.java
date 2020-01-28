package com.epam.springstart;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileEventLogger implements EventLogger {

    private String fileName;

    private File file;

    public FileEventLogger() {
    }

    public FileEventLogger(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void logEvent(Event event) {
        try {
            FileUtils.writeStringToFile(new File(fileName), event.toString() + "FILE", "UTF-8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {

        System.out.println("FILE INIT");
        this.file = new File(fileName);
        boolean can = file.canWrite();
        if (!can) {
            throw new IOException();
        }
    }

    public void destroy() {

        System.out.println("\ndestroy is empty");

    }
}
