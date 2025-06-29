package com.example.rest_library.config;

import com.example.rest_library.JWT_SECURITY.AuthEntryPointJwt;
import com.example.rest_library.JWT_SECURITY.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpMethod.POST;


@Configuration
public class SecurityConfig {





    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthTokenFilter authTokenFilter, AuthEntryPointJwt authEntryPointJwt) throws Exception
    {
        http
                .csrf(csrf -> csrf.disable()) // Wyłączenie ochrony CSRF — przydatne przy API REST lub gdy nie używasz formularzy
                //.cors(cors -> cors.disable()) // wylaczone bo przy REACT (port 3000) komunikujacym sie z serwerem (8080) musi byc skonfigurowane cors
                .cors(Customizer.withDefaults()) // taki arg -> szuka mojego beana od cors config
                // niepotrzebne po przejsciu na JWT
                /*
                .formLogin(form -> form
                        .loginPage("/index.html") // strona logowania
                        .loginProcessingUrl("/login") // URL na ktory idzie formularz - ten dostarczana przez springa jest
                        .defaultSuccessUrl("/uzytkownik.html", true) // strona po zalogowaniu
                        .permitAll()) // permit all
                */
                // serwer nie przechowuje zadnych informacji typu login, haslo. uzytkownik po swojej stronie trzyma token i to nim jest
                // weryfikowany gdy wysyła żadanie
                .sessionManagement((session ->
                {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }))

                // zakomentowane - te co niepotrzebne po przejściu na JWT
                .authorizeHttpRequests( auth -> auth// ustalamy ktore sciezki wymagaja logowania


                        //.requestMatchers("/", "/index.html", "/rejestracja.html").permitAll() // bez aut
                        .requestMatchers("/api/auth/**").permitAll() // JWT logowanie
                        //.requestMatchers( "/dodajKsiazke.html", "/dodajAutora.html").hasRole("ADMIN") //
                        .requestMatchers(POST, "/api/autorzy", "/api/ksiazki").hasRole("ADMIN") // tylko admin moze dodac ksiazke lub autora do bazy
                        //.requestMatchers("/api/**").permitAll() // wszystkie endpointy API na razie dostepn poza dodaniem ksiazki i autora
                        .anyRequest().authenticated() // wszystkie inne wymagaja logowania // po autoryzacji pozostale


                )
                // w przypadku bledu autoryzacji uzyj authEntryPoint do obslugi bledu
                .exceptionHandling(e -> e.authenticationEntryPoint(authEntryPointJwt))
                //dodaj filtr przed przed filtrem logowania springa
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
                /*

                .httpBasic(Customizer.withDefaults()); // wlacza uwierzytelnianie poprzez Auth = basic np. w Postmanie
                */

                return http.build(); // Budowanie gotowego obiektu konfiguracji bezpieczeństwa

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }


}
