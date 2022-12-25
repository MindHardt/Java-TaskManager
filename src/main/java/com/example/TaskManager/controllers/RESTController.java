package com.example.TaskManager.controllers;

import com.example.TaskManager.models.Task;
import com.example.TaskManager.models.TaskForm;
import com.example.TaskManager.repository.TaskRepository;
import com.example.TaskManager.services.TasksService;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.gson.Gson;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class RESTController {

    @Autowired
    TasksService tasksService;

    @GetMapping("/tasks")
    protected Iterable<Task> listTasks() {
        List<Task> tasks = tasksService.getAllTasks();
        tasks.sort(Comparator.comparing(Task::getTime));

        return tasks;
    }

    @PostMapping("/tasks")
    protected void addTask(HttpServletRequest req) {
        TaskForm taskForm = new TaskForm();
        taskForm.setTime(req.getParameter("task-time"));
        taskForm.setName(req.getParameter("task-name"));
        taskForm.setDescription(req.getParameter("task-description"));
        Logger.getLogger("RestLogger").log(Level.INFO, "Adding task " + taskForm);
        tasksService.addTask(taskForm.toTask());
    }
}
