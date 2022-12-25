package com.example.TaskManager.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0L;
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

    public long getId() {
        return id;
    }

    public Task(long id, LocalDateTime time, String name, String description) {
        this.id = id;
        this.time = time;
        this.name = name;
        this.description = description;
    }
    public Task () { }

    public TaskForm toForm() {
        TaskForm form = new TaskForm();
        form.setId(id);
        form.setName(name);
        form.setDescription(description);
        form.setTime(time.toString());
        return form;
    }

    @Override
    public String toString() {
        return
                id +
                " [ " + time + " ]" +
                " <" + name + ">: " +
                description;
    }
}
