package ru.sber.Archunit_Tests;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArchunitTestsApplication {

	public static void main(String[] args) {
		loadDotEnv();
		SpringApplication.run(ArchunitTestsApplication.class, args);
	}

	private static void loadDotEnv() {
		try {
			Dotenv dotenv = Dotenv.configure()
					.ignoreIfMissing()
					.ignoreIfMalformed()
					.load();
			dotenv.entries().forEach(entry -> {
				if (System.getProperty(entry.getKey()) == null) {
					System.setProperty(entry.getKey(), entry.getValue());
				}
			});
		} catch (Exception e) {
			System.out.println("No .env file loaded, relying on system environment variables.");
		}
	}

}
