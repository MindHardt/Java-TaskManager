package com.example.TaskManager.repository;

import com.example.TaskManager.models.Task;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository {
    @Autowired
    SessionFactory sessionFactory;
    public List<Task> getAllTasks() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Task> criteria = session.getCriteriaBuilder().createQuery(Task.class);
            criteria.from(Task.class);
            return session.createQuery(criteria).list();
        }
    }

    public Task getTaskById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Task task = session.find(Task.class, id);
            session.close();
            return task;
        }
    }

    public boolean addTask(Task task) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(task);
            transaction.commit();
            return true;
        }
        catch (Exception ex) {
            return false;
        }

    }

    public boolean updateTask(Task task) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(task);
            transaction.commit();
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public boolean removeTask(Task task) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(task);
            transaction.commit();
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
}
