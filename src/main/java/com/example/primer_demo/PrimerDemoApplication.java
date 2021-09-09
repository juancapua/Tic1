package com.example.primer_demo;

import com.example.primer_demo.ui.JavaFXApplication;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PrimerDemoApplication {

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		PrimerDemoApplication.context = SpringApplication.run(PrimerDemoApplication.class);

		Application.launch(JavaFXApplication.class, args);
	}

	public static ConfigurableApplicationContext getContext() {
		return context;
	}

}
