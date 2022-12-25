package com.example.TaskManager.services;

import com.example.TaskManager.models.Task;
import com.example.TaskManager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class TasksService {
    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
    public List<Task> getAllTasks() {
        return TaskRepository.TASK_REPOSITORY.getAllTasks();
    }
    public void addTask(Task task) {
        TaskRepository.TASK_REPOSITORY.addTask(task);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Adding task " + task);
        tryScheduleNotification(task);
    }

    public void updateTask(Task task) {
        TaskRepository.TASK_REPOSITORY.updateTask(task);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Updating new task " + task);
        tryScheduleNotification(task);
    }

    public Task getTask(Long id) {
        return TaskRepository.TASK_REPOSITORY.getTaskById(id);
    }

    public void removeTask(Task task) {
        TaskRepository.TASK_REPOSITORY.removeTask(task);
    }

    private void tryScheduleNotification(Task task) {
        long secondsDelay = SECONDS.between(LocalDateTime.now().plus(Duration.ofHours(1)), task.getTime());
        if (secondsDelay > 0) {
            scheduledThreadPoolExecutor.schedule(
                    getNotificationRunnable(task), secondsDelay, TimeUnit.SECONDS
            );
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Scheduled notification of task " + task);
        }
        else {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Skipping notification of task " + task);
        }
    }

    private Runnable getNotificationRunnable(Task task) {
        return () -> {
            // Checking if task still exists
            Task checkedTask = TaskRepository.TASK_REPOSITORY.getTaskById(task.getId());
            if (checkedTask != null) {
                // TODO: make email notifications
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Notifying of task " + task);
            }
            else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Task " + task + " no longer exists");
            }
        };
    }

    public TasksService() {
        for (Task task : getAllTasks()) {
            tryScheduleNotification(task);
        }
    }

}
