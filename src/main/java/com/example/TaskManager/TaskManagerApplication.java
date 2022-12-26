package com.example.TaskManager;

import com.example.TaskManager.repository.HibernateUtil;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskManagerApplication {
	@Bean(name="entityManagerFactory")
	public SessionFactory getSessionFactory() {
		return HibernateUtil.getSessionFactory();
	}

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

}
