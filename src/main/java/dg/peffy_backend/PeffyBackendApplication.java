package dg.peffy_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan 
public class PeffyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeffyBackendApplication.class, args);
	}

}
