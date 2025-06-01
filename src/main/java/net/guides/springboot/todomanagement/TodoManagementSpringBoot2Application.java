package net.guides.springboot.todomanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TodoManagementSpringBoot2Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TodoManagementSpringBoot2Application.class, args);
	}
}
