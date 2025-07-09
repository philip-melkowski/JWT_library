package com.example.rest_library.serwisy;

import com.example.rest_library.encje.Autor;
import com.example.rest_library.encje.Ksiazka;
import com.example.rest_library.repo.AutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;


    @InjectMocks
    private AutorService autorService;




    // autora jeszcze nie ma w bazie
    @Test
    public void addAutorTest1() {

    Autor autor = new Autor("Adam", "Mickiewicz");
    // mock behaviour tej metody dla repo
    when(autorRepository.findByImieAndNazwisko(autor.getImie(), autor.getNazwisko())).thenReturn(Optional.empty());
    when(autorRepository.save(autor)).thenReturn(new Autor("Adam", "Mickiewicz"));

    // when
    Autor dodany = autorService.addAutorIfNotExists(autor);

    //then
    assertEquals("Adam", dodany.getImie());
    assertEquals("Mickiewicz", dodany.getNazwisko());
    verify(autorRepository, times(1)).save(autor);












    }

}
