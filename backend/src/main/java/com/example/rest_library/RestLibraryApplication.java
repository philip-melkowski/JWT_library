package com.example.rest_library;

import com.example.rest_library.encje.Uzytkownik;
import com.example.rest_library.repo.UzytkownikRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RestLibraryApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.directory("backend")
				.ignoreIfMissing()
				.load();

		System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
		System.setProperty("DB_USER", dotenv.get("DB_USER"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		new SpringApplicationBuilder(RestLibraryApplication.class)
				.properties(
						"spring.datasource.url=jdbc:postgresql://localhost:5432/${DB_NAME}",
						"spring.datasource.username=${DB_USER}",
						"spring.datasource.password=${DB_PASSWORD}"
				)
				.run(args);
	}

	@Bean
	public CommandLineRunner initAdmin(UzytkownikRepository repo, PasswordEncoder passwordEncoder)
	{
		return args -> {
			if(!repo.existsUzytkownikByUsername("admin"))
			{
				Uzytkownik admin = new Uzytkownik();
				admin.setUsername("admin");
				admin.setImie("admin");
				admin.setNazwisko("admin");
				admin.setRola("ROLE_ADMIN");
				admin.setPassword(passwordEncoder.encode("admin"));
				repo.save(admin);
				System.out.println("Utworzono konto admina:\n username:admin\n password:admin");
			}
		};
	}

}
