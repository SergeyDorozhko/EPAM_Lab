package com.epam.springstart;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
@Component
public class Event {
    @Value("1")
    private int id;

    @Value("message")
    private String msg;
    @Value("January 27, 2020 at 4:53:28")
    private Date date;
    private DateFormat dateFormat;

    public Event(Date date, DateFormat df) {
        this.date = date;
        dateFormat = df;
        this.id =  new Random().nextInt();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", date=" + dateFormat.format(date) +
                "}\n";
    }
}
