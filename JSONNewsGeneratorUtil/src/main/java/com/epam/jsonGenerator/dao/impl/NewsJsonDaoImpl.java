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
import java.util.NoSuchElementException;
import java.util.Random;

public class NewsJsonDaoImpl implements NewsJsonDao {

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String TITLE = "title";
    private static final String SHORT_TEXT = "shortText";
    private static final String FULL_TEXT = "fullText";
    private static final String AUTHOR = "author";
    private static final String TAGS = "tags";
    private static final String NEWS = "news";
    private static final String WRONG_NAME = "wrong_name";
    private static final String FILE_EXPANSION = ".json";
    private Logger logger = LogManager.getLogger(getClass().getSimpleName());


    @Override
    public boolean write(List<News> news, String filePath, int fileType) {
        JSONArray jsonNews = generateJsonByFormat(news, fileType);
        String fileName =
                new StringBuilder()
                        .append(filePath)
                        .append(File.separator)
                        .append(JRand.string().range(1, 5).gen())
                        .append("-")
                        .append(JRand.string().range(1, 5).gen())
                        .append("-")
                        .append(JRand.string().range(1, 5).gen())
                        .append(FILE_EXPANSION)

                        .toString();
        return writeToFile(jsonNews, fileName);
    }

    private JSONArray generateJsonByFormat(List<News> news, int fileType) {
      /* 0 - valid: 16x,
         1 -wrong JSON format : 1x,
         2 - field names (in the middle of a list): 1x,
         3 - non-valid bean (in the middle of a list): 1x,
         4 - violates DB constraints (in the middle of a list): 1x
      */
        JSONArray jsonNews = new JSONArray();
        switch (fileType) {
            case 0:
                news.forEach(x -> jsonNews.add(jsonNewsCreator(x)));
                break;
            case 1:
                jsonNews.add("sadasdasdasd\"{{{{{{{{{{{{{{\"fdsfsdf{{{{{{{{{{{{{{");
                logger.info("generated wrong JSON format file");
                break;
            case 2:
                int numOfNewsWithWrongFieldName = new Random().nextInt(news.size());
                for (int i = 0; i < news.size(); i++) {
                    if (i == numOfNewsWithWrongFieldName) {
                        jsonNews.add(jsonNewsCreatorWithWrongFieldName(news.get(i)));
                        continue;
                    }
                    jsonNews.add(jsonNewsCreator(news.get(i)));
                }
                logger.info("generated wrong field name JSON file, bean#: " + numOfNewsWithWrongFieldName);

                break;
            case 3:
                int numOfNewsWithInvalidBean = new Random().nextInt(news.size());
                for (int i = 0; i < news.size(); i++) {
                    if (i == numOfNewsWithInvalidBean) {
                        jsonNews.add(jsonNewsCreatorInvalidBean(news.get(i)));
                        continue;
                    }
                    jsonNews.add(jsonNewsCreator(news.get(i)));
                }
                logger.info("generated invalid bean JSON file, bean#:" + numOfNewsWithInvalidBean);

                break;
            case 4:
                int numOfNewsWithDBconstraints = new Random().nextInt(news.size());
                for (int i = 0; i < news.size(); i++) {
                    if (i == numOfNewsWithDBconstraints) {
                        jsonNews.add(jsonNewsCreatorDBconstraints(news.get(i)));
                        continue;
                    }
                    jsonNews.add(jsonNewsCreator(news.get(i)));
                }
                logger.info("generated violates DB constraints JSON file, bean #: " + numOfNewsWithDBconstraints);

                break;
            default:
                throw new NoSuchElementException("Invalid file format");
        }
        return jsonNews;
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


    private JSONObject jsonNewsCreatorWithWrongFieldName(News news) {
        JSONArray jTags = new JSONArray();

        news.getTags().forEach(tag -> {
            JSONObject jtag = new JSONObject();
            jtag.put(WRONG_NAME, tag.getName());
            jTags.add(jtag);
        });

        JSONObject jAuthor = new JSONObject();
        if (news.getAuthor() != null) {
            jAuthor.put(WRONG_NAME, news.getAuthor().getName());
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


    private JSONObject jsonNewsCreatorInvalidBean(News news) {
        JSONArray jTags = new JSONArray();

        news.getTags().forEach(tag -> {
            JSONObject jtag = new JSONObject();
            jtag.put(NAME, "asdsa--    -- - - -fdsfds");
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

    private JSONObject jsonNewsCreatorDBconstraints(News news) {
        JSONArray jTags = new JSONArray();

        news.getTags().forEach(tag -> {
            JSONObject jtag = new JSONObject();
            jtag.put(NAME, JRand.string().range(100,1000)); // DB can store from 1 to 30 length.
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
