package com.epam.springstart;

import java.util.Collection;

public class CombinerEventLogger implements EventLogger {

    private Collection<EventLogger> loggers;

    public CombinerEventLogger(Collection<EventLogger> loggers) {
        this.loggers = loggers;
    }

    @Override
    public void logEvent(Event event) {
        for (EventLogger logger : loggers) {

            System.out.println(logger.getClass() + " collection");

            logger.logEvent(event);
        }
    }
}
