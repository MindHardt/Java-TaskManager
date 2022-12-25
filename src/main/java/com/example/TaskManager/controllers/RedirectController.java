package com.example.TaskManager.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedirectController {
    @GetMapping("/")
    public String index() {
        return "tasks-list";
    }

    @GetMapping("/add-task")
    public String addTask() {
        return "add-task";
    }
}
