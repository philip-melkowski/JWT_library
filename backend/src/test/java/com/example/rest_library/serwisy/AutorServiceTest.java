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

    // wszystkie metody delegują 1:1 do repo, wiec testy niepotrzebne, dodane niektore zeby sie nauczyc MOCK testowania

    // znajdz autora po ID ksiazki
    // ten test jest bez sensu, bo juz metody w repo jest przetestowane, a nie ma dodatkowej logiki w service
    @Test
    public void findByKsiazkiId() {

    Autor autor = new Autor("Adam", "Mickiewicz");
    Ksiazka ksiazka = new Ksiazka();
    ksiazka.setAutor(autor);
    ksiazka.setTytul("Pan Tadeusz");
    ksiazka.setId(1);
    autor.getKsiazki().add(ksiazka);

    // mock behaviour tej metody dla repo
    when(autorRepository.findByKsiazkiId(ksiazka.getId())).thenReturn(Optional.of(autor));

    // when
    Optional<Autor> autorOptional = autorService.findByKsiazkiId(ksiazka.getId());

    // thenReturn
    assertTrue(autorOptional.isPresent());
    assertEquals(autorOptional.get().getImie(), "Adam");
    assertEquals(autorOptional.get().getNazwisko(), "Mickiewicz");
    verify(autorRepository, times(1)).findByKsiazkiId(ksiazka.getId());





    }

}
