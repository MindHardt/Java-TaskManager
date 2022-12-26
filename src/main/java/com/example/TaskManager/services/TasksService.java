package com.example.TaskManager.services;

import com.example.TaskManager.models.Task;
import com.example.TaskManager.repository.TaskRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class TasksService {
    private final TaskRepository taskRepository;
    private final JavaMailSender mailSender;

    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }
    public void addTask(Task task) {
        taskRepository.addTask(task);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Adding task " + task);
        tryScheduleNotification(task, true);
    }

    public void updateTask(Task task) {
        taskRepository.updateTask(task);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Updating new task " + task);
        tryScheduleNotification(task, true);
    }

    public Task getTask(Long id) {
        return taskRepository.getTaskById(id);
    }

    public void removeTask(Task task) {
        taskRepository.removeTask(task);
    }

    private void tryScheduleNotification(Task task, boolean isNewTask) {
        long secondsDelay = SECONDS.between(LocalDateTime.now(), task.getTime());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, task + " is " + secondsDelay + " seconds in future");
        int minDelay = isNewTask ? 0 : 3600;
        if (secondsDelay > minDelay) {
            long actualDelay = isNewTask
                    ? secondsDelay < 3600 ? 0 : secondsDelay
                    : secondsDelay - 3600;

            scheduledThreadPoolExecutor.schedule(
                    getNotificationRunnable(task), actualDelay, TimeUnit.SECONDS
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
            Task checkedTask = taskRepository.getTaskById(task.getId());
            if (checkedTask != null) {
                emailNotify(checkedTask);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Notified of task " + task);
            }
            else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Task " + task + " no longer exists");
            }
        };
    }

    private void emailNotify(Task task) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Email notifying " + task);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Mindhardt@yandex.ru");
        message.setTo("Mindhardt@yandex.ru");
        message.setText(task.toString());
        message.setSubject("Task notification " + task.getName());
        mailSender.send(message);
    }

    public TasksService(TaskRepository taskRepository, JavaMailSender mailSender) {
        this.taskRepository = taskRepository;
        this.mailSender = mailSender;
        for (Task task : getAllTasks()) {
            tryScheduleNotification(task, false);
        }
    }

}
