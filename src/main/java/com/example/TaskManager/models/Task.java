package com.example.TaskManager.models;

import java.time.LocalDateTime;

public class Task {
    private LocalDateTime time;
    private String name;
    private String description;

    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
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

    public Task(LocalDateTime time, String name, String description) {
        this.time = time;
        this.name = name;
        this.description = description;
    }
}
