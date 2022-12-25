package com.example.TaskManager.repository;

import com.example.TaskManager.models.Task;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TaskRepository {
    public static final TaskRepository TASK_REPOSITORY = new TaskRepository();

    public List<Task> getAllTasks() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaQuery<Task> criteria = session.getCriteriaBuilder().createQuery(Task.class);
        criteria.from(Task.class);
        List<Task> result = session.createQuery(criteria).list();
        session.close();
        return result;
    }

    public Task getTaskById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Task task = session.find(Task.class, id);
        session.close();
        return task;
    }

    public void addTask(Task task) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(task);
        transaction.commit();
        session.close();
    }
}
