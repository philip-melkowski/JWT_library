package com.example.rest_library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.POST;


@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http
                .csrf(csrf -> csrf.disable()) // Wyłączenie ochrony CSRF — przydatne przy API REST lub gdy nie używasz formularzy
                .formLogin(form -> form
                        .loginPage("/index.html") // strona logowania
                        .loginProcessingUrl("/login") // URL na ktory idzie formularz - ten dostarczana przez springa jest
                        .defaultSuccessUrl("/uzytkownik.html", true) // strona po zalogowaniu
                        .permitAll()) // permit all

                .authorizeHttpRequests( auth -> auth// ustalamy ktore sciezki wymagaja logowania

                        .requestMatchers("/", "/index.html", "/rejestracja.html").permitAll() // bez aut
                        .requestMatchers( "/dodajKsiazke.html", "/dodajAutora.html").hasRole("ADMIN") //
                        .requestMatchers(POST, "/api/autorzy", "/api/ksiazki").hasRole("ADMIN") // tylko admin moze dodac ksiazke lub autora do bazy
                        .requestMatchers("/api/**").permitAll() // wszystkie endpointy API na razie dostepn poza dodaniem ksiazki i autora
                            .anyRequest().authenticated() // wszystkie inne wymagaja logowania // po autoryzacji pozostale

                )
                .httpBasic(Customizer.withDefaults()); // wlacza uwierzytelnianie poprzez Auth = basic np. w Postmanie

                return http.build(); // Budowanie gotowego obiektu konfiguracji bezpieczeństwa

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }
}
