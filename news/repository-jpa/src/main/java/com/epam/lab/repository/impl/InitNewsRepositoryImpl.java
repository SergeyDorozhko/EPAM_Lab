package com.epam.lab.repository.impl;

import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.InitNewsRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class InitNewsRepositoryImpl implements InitNewsRepository {
    private static final String SURNAME = "surname";
    private static final String NAME = "name";
    private static final String AUTHOR = "author";
    private static final String TAGS = "tags";
    private static final String FULL_TEXT = "fullText";
    private static final String SHORT_TEXT = "shortText";
    private static final String TITLE = "title";
    private static final String NEWS = "news";
    private static final String LOGS_FOLDER = "logs";
    private static final String ERROR_FOLDER = "error";


    @Override
    public void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    @Override
    public void restoreFile(String file, String pathTo) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmm"));
        File errorFolder
                = new File(pathTo
                + "error"
                + File.separator
                + time + File.separator);
        errorFolder.mkdirs();
        File operationFile = new File(file);
        operationFile.renameTo(
                new File( errorFolder.getPath()
                        + File.separator
                        + operationFile.getName()));
    }

    @Override
    public List<String> findAllFiles(String rootPath) {
        List<String> files = new LinkedList<>();
        File path = new File(rootPath);
        for (File currentPath : path.listFiles()) {
            if (currentPath.isFile()) {
                files.add(currentPath.getPath());
            } else if (currentPath.isDirectory()
                    && currentPath.getName().equals(LOGS_FOLDER)
                    || currentPath.getName().equals(ERROR_FOLDER)) {
                continue;
            } else {
                files.addAll(
                        findAllFiles(currentPath.getPath()));
            }
        }
        return files;
    }

    @Override
    public List<News> readCatalog(String path) {
        System.out.println(path);
        JSONParser jsonParser = new JSONParser();

        List<News> newsFromFile = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray news = (JSONArray) obj;

            //Iterate over employee array
            news.forEach(currNews -> {
                newsFromFile.add(parseNewsObject((JSONObject) currNews));
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newsFromFile;
    }

    private News parseNewsObject(JSONObject news) {
        News createNews = new News();

        JSONObject newsObject = (JSONObject) news.get(NEWS);

        createNews.setTitle((String) newsObject.get(TITLE));
        createNews.setShortText((String) newsObject.get(SHORT_TEXT));
        createNews.setFullText((String) newsObject.get(FULL_TEXT));
        createNews.setAuthor(parseAuthor(newsObject));
        createNews.setTags(parseTags(newsObject));

        return createNews;
    }

    private List<Tag> parseTags(JSONObject newsObject) {
        JSONArray tagsArray = (JSONArray) newsObject.get(TAGS);
        List<Tag> tags = new ArrayList<>();
        tagsArray.forEach(currTag -> {
            Tag tag = new Tag();
            tag.setName((String) ((JSONObject) currTag).get(NAME));
            tags.add(tag);
        });
        return tags;
    }

    private Author parseAuthor(JSONObject newsObject) {
        JSONObject authorObject = (JSONObject) newsObject.get(AUTHOR);
        Author author = new Author();
        author.setName((String) authorObject.get(NAME));
        author.setSurname((String) authorObject.get(SURNAME));
        return author;
    }


    @Override
    public News create(News bean) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public News update(News bean) {
        throw new UnsupportedOperationException();
    }

    @Override
    public News findBy(long id) {
        throw new UnsupportedOperationException();
    }
}
