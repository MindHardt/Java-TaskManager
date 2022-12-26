package com.example.TaskManager;

import com.example.TaskManager.controllers.RESTController;
import com.example.TaskManager.repository.TaskRepository;
import com.example.TaskManager.services.TasksService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class IntegrationTests {
    @Autowired
    private RESTController restController;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TasksService tasksService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkRest() {
        assertThat(restController).isNotNull();
    }

    @Test
    public void getTasks () throws Exception {
        this.mockMvc.perform(get("/api/tasks"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void postAddProduct() throws Exception {
        String taskName = "nameTest";
        this.mockMvc.perform(post("/api/tasks/add")
                        .param("task-name", taskName)
                        .param("task-description", "Test task")
                        .param("task-time", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(status().isFound());


    }
}
