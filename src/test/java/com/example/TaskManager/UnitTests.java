package com.example.TaskManager;

import com.example.TaskManager.models.Task;
import com.example.TaskManager.models.TaskForm;
import com.example.TaskManager.repository.TaskRepository;
import com.example.TaskManager.services.TasksService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UnitTests {

    @Autowired
    private TasksService tasksService;
    @MockBean
    private TaskRepository taskRepository;

    @Test
    public void addTask() {
        String testName = "test task";

        TaskForm form = new TaskForm();
        form.setName(testName);
        form.setDescription("This is a test task");
        form.setTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        Task task = form.toTask();
        boolean saved = taskRepository.addTask(task);

        assertThat(saved).isFalse();
        assertThat(task.getName()).isEqualTo(testName);
    }

    @Test
    public void deleteTask() {
        String testName = "test task";

        TaskForm form = new TaskForm();
        form.setName(testName);
        form.setDescription("This is a test task");
        form.setTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        Task task = form.toTask();

        boolean added = taskRepository.addTask(task);
        boolean deleted = taskRepository.removeTask(task);
        List<Task> tasks = taskRepository.getAllTasks();

        assertThat(added).isFalse();
        assertThat(deleted).isFalse();
        assertThat(tasks).doesNotContain(task);
    }
}
