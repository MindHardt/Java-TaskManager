package com.example.TaskManager.controllers;

import com.example.TaskManager.models.Task;
import com.example.TaskManager.models.TaskForm;
import com.example.TaskManager.services.TasksService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
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

    @GetMapping("/tasks/{id}")
    protected Task listTasks(@PathVariable Long id) {
        return tasksService.getTask(id);
    }

    @PostMapping("/tasks/add")
    protected void addTask(HttpServletRequest req, HttpServletResponse resp) {
        Task task = getTaskFromRequest(req);

        logRequestParams(req);

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Adding task " + task);
        tasksService.addTask(task);
        try {
            resp.sendRedirect("/");
        }
        catch (IOException ignored) {

        }
    }

    @PostMapping("/tasks/update")
    protected void updateTask(HttpServletRequest req, HttpServletResponse resp) {
        Task task = getTaskFromRequest(req);

        logRequestParams(req);

        if (Objects.equals(req.getParameter("task-delete"), "on")) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Deleting task " + task);
            tasksService.removeTask(task);
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Updating task " + task);
            tasksService.updateTask(task);
        }
        try {
            resp.sendRedirect("/update");
        }
        catch (IOException ignored) {

        }
    }

    private Task getTaskFromRequest(HttpServletRequest req) {
        TaskForm taskForm = new TaskForm();

        String id = req.getParameter("task-id");
        if (id != null) {
            taskForm.setId(Long.parseLong(id));
        }

        taskForm.setTime(req.getParameter("task-time"));
        taskForm.setName(req.getParameter("task-name"));
        taskForm.setDescription(req.getParameter("task-description"));

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Updating task " + taskForm);
        return taskForm.toTask();
    }

    private void logRequestParams(HttpServletRequest req) {
        Map<String, String[]> map = req.getParameterMap();
        List<String> params = new ArrayList<>();
        for (String key : map.keySet()) {
            params.add(key + " " + Arrays.toString(map.get(key)));
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Transforming form " + String.join("; ", params));
    }
}
