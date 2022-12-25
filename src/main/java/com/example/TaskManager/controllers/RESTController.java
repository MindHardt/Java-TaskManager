package com.example.TaskManager.controllers;

import com.example.TaskManager.models.Task;
import com.example.TaskManager.repository.TaskRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RESTController {

    @GetMapping("/tasks")
    protected Iterable<Task> listTasks() {
        List<Task> tasks = TaskRepository.TASK_REPOSITORY.getAllTasks();
        tasks.sort(Comparator.comparing(Task::getTime));

        return tasks;
    }

    @PostMapping("/tasks")
    protected void addTask(HttpServletRequest req) {
        String rawTime = req.getParameter("time");
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        LocalDateTime time = LocalDateTime.parse(rawTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Task task = new Task(time, name, description);
        TaskRepository.TASK_REPOSITORY.addTask(task);
    }
}
