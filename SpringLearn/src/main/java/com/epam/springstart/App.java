package com.epam.springstart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Configuration
public class App {


    @Qualifier("Client")
    private Client client;

    @Qualifier("ConsoleEventLogger")
    private EventLogger eventLogger;
//    private Map<EventType, EventLogger> loggers;
//


//    public App(Client client, EventLogger eventLogger, Map<EventType, EventLogger> loggers) {
//        this.client = client;
//        this.eventLogger = eventLogger;
////        this.loggers = loggers;
//    }

    private void logEvent(EventType type, Event event) {
//        EventLogger logger = loggers.get(type);
//        if (logger == null) {
//            logger = eventLogger;
//        }
//        System.out.println(logger.getClass());
        event.setMsg(event.getMsg().replaceAll(client.getId(), client.getFullName()) + " " + client.getGreating());
        eventLogger.logEvent(event);
    }

    public static void main(String[] args) {
//        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        App app = (App) context.getBean("app");
        Event event = (Event) context.getBean("event");
        event.setMsg("Some event INFO from user 1");
        app.logEvent(EventType.INFO, event);

        event.setMsg("Some event ERROR from user 1");
        app.logEvent(EventType.ERROR, event);
        event.setMsg("Some event ERROR from user 1");
        app.logEvent(EventType.ERROR, event);
        event.setMsg("Some event ERROR from user 1");
        app.logEvent(EventType.ERROR, event);
        event.setMsg("Some event ERROR from user 1");
        app.logEvent(EventType.ERROR, event);

        event.setMsg("Some event ERROR from user 1");
        app.logEvent(EventType.ERROR, event);

        event.setMsg("Some event ERROR from user 1");
        app.logEvent(null, event);

//        context.close();
    }
}
