package com.example.TaskManager.services;

import com.example.TaskManager.models.Task;
import com.example.TaskManager.repository.TaskRepository;
import org.hibernate.type.descriptor.DateTimeUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;

public class TasksService {
    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
    public List<Task> getAllTasks() {
        return TaskRepository.TASK_REPOSITORY.getAllTasks();
    }
    public void addTask(Task task) {
        TaskRepository.TASK_REPOSITORY.addTask(task);
    }
    public void removeTask(Task task) {
        TaskRepository.TASK_REPOSITORY.removeTask(task);
    }

    private void tryScheduleNotification(Task task) {
        long secondsDelay = SECONDS.between(task.getTime(), LocalDateTime.now().plus(Duration.ofHours(1)));
        if (secondsDelay > 0) {
            scheduledThreadPoolExecutor.schedule(
                    getNotificationRunnable(task), secondsDelay, TimeUnit.SECONDS
            );
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Scheduled notification of task " + task);
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "skipping notification of task " + task);
    }

    private Runnable getNotificationRunnable(Task task) {
        return () -> {
            // TODO: make email notifications
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Notifying of task " + task);
        };
    }

    public TasksService() {
        for (Task task : getAllTasks()) {
            tryScheduleNotification(task);
        }
    }

}
