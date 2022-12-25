package com.example.TaskManager.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskForm {
    private String time;
    private String name;
    private String description;

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public TaskForm() {

    }

    public Task toTask() {
        LocalDateTime parsedTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return new Task(parsedTime, name, description);
    }

    @Override
    public String toString() {
        return
                        " [ " + time + " ]" +
                        " <" + name + ">: " +
                        description;
    }
}
