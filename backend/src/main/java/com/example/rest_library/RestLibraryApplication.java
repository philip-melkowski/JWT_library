package com.example.rest_library;

import com.example.rest_library.encje.Uzytkownik;
import com.example.rest_library.repo.UzytkownikRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class RestLibraryApplication {
 
	public static void main(String[] args) {


		SpringApplication.run(RestLibraryApplication.class, args);
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
