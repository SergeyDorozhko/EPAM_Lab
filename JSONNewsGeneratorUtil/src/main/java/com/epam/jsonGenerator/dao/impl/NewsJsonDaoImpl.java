package com.epam.jsonGenerator.dao.impl;

import com.epam.jsonGenerator.dao.NewsJsonDao;
import com.epam.jsonGenerator.dao.bean.News;
import me.xdrop.jrand.JRand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class NewsJsonDaoImpl implements NewsJsonDao {

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String TITLE = "title";
    private static final String SHORT_TEXT = "shortText";
    private static final String FULL_TEXT = "fullText";
    private static final String AUTHOR = "author";
    private static final String TAGS = "tags";
    private static final String NEWS = "news";
    private Logger logger = LogManager.getLogger(getClass().getSimpleName());


    @Override
    public boolean write(List<News> news, String filePath) {
        JSONArray jsonNews = new JSONArray();
        news.forEach(news1 -> jsonNews.add(jsonNewsCreator(news1)));
        //Write JSON file
        String fileName =
                new StringBuilder()
                        .append(filePath)
                        .append(File.separator)
                        .append(JRand.string().range(1,5).gen())
                        .append("-")
                        .append(JRand.string().range(1,5).gen())
                        .append("-")
                        .append(JRand.string().range(1,5).gen())
                        .append(".json")
                        .toString();
        return writeToFile(jsonNews, fileName);
    }

    private JSONObject jsonNewsCreator(News news) {
        JSONArray jTags = new JSONArray();

        news.getTags().forEach(tag -> {
            JSONObject jtag = new JSONObject();
            jtag.put(NAME, tag.getName());
            jTags.add(jtag);
        });

        JSONObject jAuthor = new JSONObject();
        if (news.getAuthor() != null) {
            jAuthor.put(NAME, news.getAuthor().getName());
            jAuthor.put(SURNAME, news.getAuthor().getSurname());
        }

        JSONObject jNews = new JSONObject();
        jNews.put(TITLE, news.getTitle());
        jNews.put(SHORT_TEXT, news.getShortText());
        jNews.put(FULL_TEXT, news.getFullText());
        jNews.put(AUTHOR, jAuthor);
        jNews.put(TAGS, jTags);

        JSONObject jNewsObject = new JSONObject();
        jNewsObject.put(NEWS, jNews);

        return jNewsObject;
    }

    private boolean writeToFile(JSONArray jsonNews, String fileName) {
        try (BufferedWriter file = new BufferedWriter(new FileWriter(fileName))) {
            file.write(jsonNews.toJSONString());
            file.flush();
        } catch (IOException e) {
            logger.error(String.format("CREATE NEW FILE: \n%s20 FILE : %s \n%20s %s", "", fileName, "", e.getMessage()));
            return false;
        }
        logger.info(String.format("SUCCESS \n%20s CREATE NEW FILE: %s", "", fileName));
        return true;
    }
}
