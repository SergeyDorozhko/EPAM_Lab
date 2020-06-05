package com.epam.jsonGenerator.service.impl;

import com.epam.jsonGenerator.dao.FactoryDao;
import com.epam.jsonGenerator.dao.bean.Author;
import com.epam.jsonGenerator.dao.bean.News;
import com.epam.jsonGenerator.dao.bean.Tag;
import com.epam.jsonGenerator.service.NewsService;
import me.xdrop.jrand.JRand;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NewsServiceImpl implements NewsService {
    long taskTime;
    int filesCount;
    long periodTime;

    public NewsServiceImpl() {
        readProperties();
    }

    private void readProperties() {
        ResourceBundle rb = ResourceBundle.getBundle("app");
        taskTime = Long.valueOf(rb.getString("testTime"));
        filesCount = Integer.valueOf(rb.getString("filesCount"));
        periodTime = Long.valueOf(rb.getString("periodTime"));
    }


    @Override
    public void generatorFile(String basePath) {

        System.out.println("start");
        File folder = new File(basePath);

        List<TimerTask> tasks = new ArrayList<>();
        generateTasks(folder, tasks);

        List<Timer> timers = new ArrayList<>();
        for (TimerTask task : tasks) {
            Timer timer = new Timer();
            timer.schedule(task, 0, periodTime);
            timers.add(timer);
        }

        System.out.println("finish start task");

        try {
            System.out.println(Thread.currentThread().getName() + "   SLEEP");
            Thread.sleep(TimeUnit.SECONDS.toMillis(taskTime));
            System.out.println(Thread.currentThread().getName() + "   Wake UP");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timers.forEach(Timer::cancel);
    }


    private void generateTasks(File folder, List<TimerTask> tasks) {

        tasks.add(createTimerTask(folder));
        if (folder.list().length != 0) {
            for (File subfolder : folder.listFiles()) {
                if (subfolder.isDirectory() && !subfolder.getName().equals("logs") && !subfolder.getName().equals("error")) {
                    System.out.println(subfolder.getPath());
                    generateTasks(subfolder, tasks);
                }
            }
        }
    }

    private TimerTask createTimerTask(File folder) {
        return new TimerTask() {
            @Override
            public void run() {
                System.out.println("START" + Thread.currentThread().getName() + " -------------- " + LocalDateTime.now());
                generateNews(folder);
                System.out.println("FINISH --------- " + Thread.currentThread().getName() + " ---------------- " + LocalDateTime.now());
            }
        };
    }

    private void generateNews(File folder) {

        //TODO from properties    countFiles
        int countFiles = filesCount;
        while (countFiles > 0) {
            countFiles--;
            List<News> news = new ArrayList<>();
            int numberOfNews = new Random().nextInt(20) + 3;
            for (int i = 0; i < numberOfNews; i++) {
                news.add(createOneNewsWithRandomValues());
            }
            FactoryDao.getInstance().getNewsJsonDao().write(news, folder.getPath());
        }

    }

    private News createOneNewsWithRandomValues() {

        News news = new News();
        Author author = new Author();
        author.setName(JRand.string().range(1,30).gen());
        author.setSurname(JRand.string().range(1,30).gen());
        news.setAuthor(author);

        for (int i = 0; i < new Random().nextInt(3); i++) {
            Tag tag = new Tag();
            tag.setName(JRand.string().range(2,30).gen());
            news.addTag(tag);
        }

        news.setTitle(JRand.string().range(5,30).gen());
        news.setShortText(JRand.string().range(10,100).gen());
        news.setFullText(JRand.string().range(100,2000).gen());
        return news;
    }
}
