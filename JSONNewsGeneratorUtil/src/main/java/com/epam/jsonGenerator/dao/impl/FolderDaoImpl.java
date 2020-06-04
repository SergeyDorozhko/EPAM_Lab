package com.epam.jsonGenerator.dao.impl;

import com.epam.jsonGenerator.dao.FolderDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FolderDaoImpl implements FolderDao {

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    @Override
    public void create(int subFoldersCount, String basePath) {
        int firstLevelFoldersCount = subFoldersCount / 3;
        List<String> paths = new ArrayList<>();
        while (firstLevelFoldersCount != 0) {
            String folderName = basePath;
            Random random = new Random();
            int max = subFoldersCount - firstLevelFoldersCount + 1;
            int depth = firstLevelFoldersCount == 1 ? subFoldersCount : 1 + random.nextInt(max);
            firstLevelFoldersCount--;
            subFoldersCount -= depth;
            for (int i = 0; i < depth; i++) {
                int layerNumber = i + 1;
                int sequenceNumber = 0;
                folderName = folderName + "\\" + layerNumber + "st" + "LayerSubFolder" + sequenceNumber;
                while (paths.contains(folderName)) {
                    sequenceNumber++;
                    folderName = folderName.substring(0, folderName.length() - 1) + sequenceNumber;
                }
                paths.add(folderName);
                File file = new File(folderName);
                logger.info(String.format("CREATE:\n%20s PATH : %s \n%20s CATALOG NAME : %s", "", file.getParentFile().getPath(), "", file.getName()));
                file.mkdirs();
            }
        }
    }

    @Override
    public void clearBaseDirectory(String basePath) {
        File file = new File(basePath);
        for (File removeFile : file.listFiles()) {
            if (!removeFile.getName().equals("logs") && !removeFile.getName().equals("error")) {
                if (!removeFile.isFile() && removeFile.list().length != 0) {
                    clearBaseDirectory(removeFile.getPath());
                }

                logger.info(String.format("DETELE:\n%20s PATH : %s\n%20s FOLDER or FILE NAME : %s ", "", removeFile.getParentFile().getPath(), "", removeFile.getName()));
                removeFile.delete();
            }
        }
    }
}
