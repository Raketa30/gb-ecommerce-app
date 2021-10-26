package ru.geekbrains.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.geekbrains.backend.security.configuration.SwaggerConfiguration;

@SpringBootApplication
@Import(SwaggerConfiguration.class)
public class OnlineShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineShopApplication.class, args);
	}

}
