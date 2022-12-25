package com.example.TaskManager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
    @GetMapping("/")
    public String index() {
        return "tasks-list";
    }

    @GetMapping("/add")
    public String add() {
        return "add-task";
    }

    @GetMapping("/update")
    public String edit() {
        return "update-tasks";
    }
}
