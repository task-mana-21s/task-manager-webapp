package lt.viko.eif.pss.taskmanagerwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class TaskManagerWebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerWebappApplication.class, args);
	}

	// http://localhost:8080
	// http://localhost:8080/api
	// http://localhost:8080/v3/api-docs
	// http://localhost:8080/v3/api-docs.yaml
	// http://localhost:8080/swagger-ui/index.html

}

