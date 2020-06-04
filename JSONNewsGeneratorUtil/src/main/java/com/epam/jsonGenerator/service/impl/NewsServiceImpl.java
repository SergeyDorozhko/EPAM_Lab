package com.epam.jsonGenerator.service.impl;

import com.epam.jsonGenerator.dao.FactoryDao;
import com.epam.jsonGenerator.dao.bean.Author;
import com.epam.jsonGenerator.dao.bean.News;
import com.epam.jsonGenerator.dao.bean.Tag;
import com.epam.jsonGenerator.service.NewsService;
import com.github.javafaker.Faker;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NewsServiceImpl implements NewsService {

    private List<TimerTask> tasks = new ArrayList<>();

//    @Override
//    public void generator(String basePath, long endTime) {
//        System.out.println(Thread.currentThread().getName());
//        File folder = new File(basePath);
//
//        if (folder.list().length != 0) {
//            for (File subfolder : folder.listFiles()) {
//                if (subfolder.isDirectory() && !subfolder.getName().equals("logs") && !subfolder.getName().equals("error")) {
//                    Timer timer = new Timer();
//                    TimerTask timerTask = new TimerTask() {
//                        @Override
//                        public void run() {
//                            generator(subfolder.getPath(), endTime);
//                            if (System.currentTimeMillis() >= endTime) {
//                                System.out.println("TIMER CANCEL" + Thread.currentThread().getName());
//                                timer.cancel();
//                            }
//                        }
//                    };
//                    timer.schedule(timerTask, 0, 80);
//                }
//            }
//        }
//
//
//        int countFiles = 10;
//        while (countFiles > 0) {
//            countFiles--;
//            List<News> newsList = new ArrayList<>();
//            for (int i = 0; i < new Random().nextInt(20) + 3; i++) {
//                News news = createOneNewsWithRandomValues();
//                newsList.add(news);
//            }
//            FactoryDao.getInstance().getNewsJsonDao().write(newsList, basePath);
//        }
//
//    }


    @Override
    public void generator(String basePath, long endTime) {
        System.out.println("start");
        File folder = new File(basePath);
        tasks = generateTasks(folder);

        List<Timer> timers = new ArrayList<>();
        for (TimerTask task : tasks) {
            Timer timer = new Timer();
            timer.schedule(task, 0, 80);
            timers.add(timer);
        }

        System.out.println("finish start task");
        try {
            System.out.println(Thread.currentThread().getName() + "   SLEEP");
            Thread.sleep(TimeUnit.SECONDS.toMillis(endTime));
            System.out.println(Thread.currentThread().getName() + "   Wake UP");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timers.forEach(Timer::cancel);

    }

    private List<TimerTask> generateTasks(File folder) {
        tasks.add(createTimerTask(folder));
        if (folder.list().length != 0) {
            for (File subfolder : folder.listFiles()) {
                if (subfolder.isDirectory() && !subfolder.getName().equals("logs") && !subfolder.getName().equals("error")) {
                    System.out.println(subfolder.getPath());
                    generateTasks(subfolder);
                    tasks.add(createTimerTask(subfolder));
                }
            }
        }
        return tasks;
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

        //TODO from properties    countFiles
        int countFiles = 10;
        while (countFiles > 0) {
            countFiles--;
            int numberOfNews = new Random().nextInt(20) + 3;
            List<News> news = new ArrayList<>();
            for (int i = 0; i < numberOfNews; i++) {
                news.add(createOneNewsWithRandomValues());
            }
            FactoryDao.getInstance().getNewsJsonDao().write(news, folder.getPath());
        }

    }

    private News createOneNewsWithRandomValues() {

        Faker faker = new Faker();

        News news = new News();
        Author author = new Author();
        author.setName(faker.name().firstName());
        author.setSurname(faker.name().lastName());
        news.setAuthor(author);

        for(int i = 0; i < new Random().nextInt(3); i++) {
            Tag tag = new Tag();
            tag.setName(faker.book().genre());
            news.addTag(tag);
        }

        news.setTitle(faker.book().title());
        news.setShortText(faker.regexify("[\\w]{10,100}"));
        news.setFullText(faker.regexify("[\\w]{10,2000}"));
        return news;
    }

}
