package com.example.rest_library.controllery;

import com.example.rest_library.JWT_SECURITY.AuthTokenFilter;
import com.example.rest_library.config.SecurityConfig;
import com.example.rest_library.encje.Autor;
import com.example.rest_library.serwisy.AutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// testy dla metod, co w zaleznosci od sytuacji zwracać mogą różne statusy
@WebMvcTest(controllers = AutorController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class
        })
@ActiveProfiles("test")
public class AutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AutorService autorService;

    @MockBean
    private AuthTokenFilter authTokenFilter;


    @Test
    public void findByImieAndNazwiskoTest1() throws Exception
    {
        Autor autor = new Autor("Adam", "Mickiewicz");

        when(autorService.findByImieAndNazwisko("Adam", "Mickiewicz")).thenReturn(Optional.of(autor));

        mockMvc.perform(get("/api/autorzy/szukajByImieNazwisko")
                .param("imie", autor.getImie())
                .param("nazwisko", autor.getNazwisko())
        )
                .andDo(print());
                //.andExpect(status().isOk())
                //.andExpect(jsonPath("$.imie").value("Adam"))
                //.andExpect(jsonPath("$.nazwisko").value("Mickiewicz"));
    }






}
