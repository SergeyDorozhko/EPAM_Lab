package com.epam.jsonGenerator.service.impl;

import com.epam.jsonGenerator.dao.FactoryDao;
import com.epam.jsonGenerator.dao.bean.Author;
import com.epam.jsonGenerator.dao.bean.News;
import com.epam.jsonGenerator.dao.bean.Tag;
import com.epam.jsonGenerator.service.NewsService;
import me.xdrop.jrand.JRand;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NewsServiceImpl implements NewsService {
    private static final String LOGS_FOLDER = "logs";
    private static final String ERROR_FOLDER = "error";
    private static final String PROPERTY_FILE_NAME = "app";
    private static final String TEST_TIME = "testTime";
    private static final String FILES_COUNT = "filesCount";
    private static final String PERIOD_TIME = "periodTime";
    long taskTime;
    int filesCount;
    long periodTime;

    public NewsServiceImpl() {
        readProperties();
    }

    private void readProperties() {
        ResourceBundle rb = ResourceBundle.getBundle(PROPERTY_FILE_NAME);
        taskTime = Long.valueOf(rb.getString(TEST_TIME));
        filesCount = Integer.valueOf(rb.getString(FILES_COUNT));
        periodTime = Long.valueOf(rb.getString(PERIOD_TIME));
    }


    @Override
    public void generatorFile(String basePath) {

        File folder = new File(basePath);

        List<TimerTask> tasks = new ArrayList<>();
        generateTasks(folder, tasks);

        List<Timer> timers = new ArrayList<>();
        for (TimerTask task : tasks) {
            Timer timer = new Timer();
            timer.schedule(task, 0, periodTime);
            timers.add(timer);
        }


        try {
           Thread.sleep(TimeUnit.SECONDS.toMillis(taskTime));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timers.forEach(Timer::cancel);
    }


    private void generateTasks(File folder, List<TimerTask> tasks) {

        tasks.add(createTimerTask(folder));
        if (folder.list().length != 0) {
            for (File subfolder : folder.listFiles()) {
                if (subfolder.isDirectory() && !subfolder.getName().equals(LOGS_FOLDER) && !subfolder.getName().equals(ERROR_FOLDER)) {
                    generateTasks(subfolder, tasks);
                }
            }
        }
    }

    private TimerTask createTimerTask(File folder) {
        return new TimerTask() {
            @Override
            public void run() {
                generateNews(folder);
            }
        };
    }

    private void generateNews(File folder) {

        Queue<Integer> fileType = genOrderOfCorrectAndIncorrectFiles();


        int countFiles = filesCount;
        while (countFiles > 0) {
            countFiles--;
            List<News> news = new ArrayList<>();
            int numberOfNews = new Random().nextInt(20) + 3;
            for (int i = 0; i < numberOfNews; i++) {
                news.add(createOneNewsWithRandomValues());
            }
            FactoryDao.getInstance().getNewsJsonDao().write(news, folder.getPath(), fileType.poll());
        }

    }

    private News createOneNewsWithRandomValues() {

        News news = new News();
        Author author = new Author();
        author.setName(JRand.string().range(1, 30).gen());
        author.setSurname(JRand.string().range(1, 30).gen());
        news.setAuthor(author);

        for (int i = 0; i < new Random().nextInt(3); i++) {
            Tag tag = new Tag();
            tag.setName(JRand.string().range(2, 30).gen());
            news.addTag(tag);
        }

        news.setTitle(JRand.string().range(5, 30).gen());
        news.setShortText(JRand.string().range(10, 100).gen());
        news.setFullText(JRand.string().range(100, 2000).gen());
        return news;
    }

    private LinkedList<Integer> genOrderOfCorrectAndIncorrectFiles() {
      /* 0 - valid: 16x,
         1 -wrong JSON format : 1x,
         2 - field names (in the middle of a list): 1x,
         3 - non-valid bean (in the middle of a list): 1x,
         4 - violates DB constraints (in the middle of a list): 1x
      */
        LinkedList<Integer> order = new LinkedList<>();
        for (int i = 0; i < filesCount / 20 * 16; i++) {
            order.add(0);
        }
        for (int i = 0; i < filesCount / 20 * 1; i++) {
            order.add(1);
            order.add(2);
            order.add(3);
            order.add(4);
        }
        Collections.shuffle(order);
        return order;
    }
}
