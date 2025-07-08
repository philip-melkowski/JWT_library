package com.example.rest_library.repo;


import com.example.rest_library.encje.Autor;
import com.example.rest_library.encje.Ksiazka;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class AutorRepositoryTest {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private KsiazkaRepository ksiazkaRepository;

    // znajduje jedyny matchujacy rekord
    @Test
    public void findByNazwiskoTest() {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        List<Autor> autorList = autorRepository.findByNazwisko("Mickiewicz");
        assertFalse(autorList.isEmpty());
        assertEquals(autor, autorList.getFirst());
    }

    // zwraca pusta liste, bo nie ma zadnego rekordu
    @Test
    public void findByNazwiskoTest2()
    {
        List<Autor> autorList = autorRepository.findByNazwisko("Mickiewicz");
        assertTrue(autorList.isEmpty());
    }



    // zwraca pusta liste, bo nie ma autora o danym nazwisku
    @Test
    public void findByNazwiskoTest3()
    {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        List<Autor> autorList = autorRepository.findByNazwisko("Prus");
        assertTrue(autorList.isEmpty());
    }
    // zwraca liste 2 autorow o tym samym nazwisku
    @Test
    public void findByNazwiskoTest4()
    {
        Autor autor1 = new Autor("Adam", "Mickiewicz");
        Autor autor2 = new Autor("Adam2", "Mickiewicz");
        autorRepository.save(autor1);
        autorRepository.save(autor2);
        List<Autor> autorList = autorRepository.findByNazwisko("Mickiewicz");
        assertFalse(autorList.isEmpty());
        assertEquals(2, autorList.size());
    }


    // zwraca pusta liste bo case sensitivity
    @Test
    public void findByNazwiskoTest5()
    {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        List<Autor> autorList = autorRepository.findByNazwisko("mickiewicz");
        assertTrue(autorList.isEmpty());
    }

    // obsluga null jako argumentu
    @Test
    public void findByNazwiskoTest6()
    {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        List<Autor> autorList = autorRepository.findByNazwisko(null);
        assertTrue(autorList.isEmpty());
    }

    // pusty string w nazwisku
    @Test
    public void findByNazwiskoTest7()
    {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        List<Autor> autorList = autorRepository.findByNazwisko("");
        assertTrue(autorList.isEmpty());
    }

    // znajduje jedyny matchujacy rekord po imieniu
    @Test
    public void findByImieTest1() {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        List<Autor> autorList = autorRepository.findByImie("Adam");
        assertFalse(autorList.isEmpty());
        assertEquals(autor, autorList.getFirst());
    }

    // zwraca pustą listę, bo nie ma żadnego rekordu
    @Test
    public void findByImieTest2() {
        List<Autor> autorList = autorRepository.findByImie("Adam");
        assertTrue(autorList.isEmpty());
    }

    // zwraca pustą listę, bo nie ma autora o danym imieniu
    @Test
    public void findByImieTest3() {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        List<Autor> autorList = autorRepository.findByImie("Jan");
        assertTrue(autorList.isEmpty());
    }

    // zwraca listę 2 autorów o tym samym imieniu
    @Test
    public void findByImieTest4() {
        Autor autor1 = new Autor("Adam", "Mickiewicz");
        Autor autor2 = new Autor("Adam", "Prus");
        autorRepository.save(autor1);
        autorRepository.save(autor2);
        List<Autor> autorList = autorRepository.findByImie("Adam");
        assertFalse(autorList.isEmpty());
        assertEquals(2, autorList.size());
    }

    // zwraca pustą listę bo case sensitivity
    @Test
    public void findByImieTest5() {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        List<Autor> autorList = autorRepository.findByImie("adam");
        assertTrue(autorList.isEmpty());
    }

    // obsługa null jako argumentu
    @Test
    public void findByImieTest6() {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        List<Autor> autorList = autorRepository.findByImie(null);
        assertTrue(autorList.isEmpty());
    }

    // pusty string w imieniu
    @Test
    public void findByImieTest7() {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        List<Autor> autorList = autorRepository.findByImie("");
        assertTrue(autorList.isEmpty());
    }

    // ma zwrocic jedyny rekord
    @Test
    public void findByImieAndNazwisko1()
    {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        Optional<Autor> autorOptional = autorRepository.findByImieAndNazwisko("Adam", "Mickiewicz");
        assertTrue(autorOptional.isPresent());
        assertEquals(autor, autorOptional.get());
    }

    // ma zwrocic jeden z dwoch rekordow, ktore dziela imie
    @Test
    public void findByImieAndNazwisko2()
    {
        Autor autor1 = new Autor("Adam", "Mickiewicz");
        Autor autor2 = new Autor("Adam", "Prus");
        autorRepository.save(autor1);
        autorRepository.save(autor2);
        Optional<Autor> autorOptional = autorRepository.findByImieAndNazwisko("Adam", "Mickiewicz");
        assertTrue(autorOptional.isPresent());
        assertEquals(autor1, autorOptional.get());
    }

    // ma zwrocic jeden z dwoch rekordow, ktore dziela nazwisko
    @Test
    public void findByImieAndNazwisko3()
    {
        Autor autor1 = new Autor("Adam", "Mickiewicz");
        Autor autor2 = new Autor("Jan", "Mickiewicz"); // zalozmy ze ktos taki istnieje :P
        autorRepository.save(autor1);
        autorRepository.save(autor2);
        Optional<Autor> autorOptional = autorRepository.findByImieAndNazwisko("Adam", "Mickiewicz");
        assertTrue(autorOptional.isPresent());
        assertEquals(autor1, autorOptional.get());
    }

    // ma zwrocic zaden z 2 rekordow
    @Test
    public void findByImieAndNazwisko4()
    {
        Autor autor1 = new Autor("Adam", "Mickiewicz");
        Autor autor2 = new Autor("Boleslaw", "Prus");
        autorRepository.save(autor1);
        autorRepository.save(autor2);
        Optional<Autor> autorOptional = autorRepository.findByImieAndNazwisko("Haruki", "Murakami");
        assertFalse(autorOptional.isPresent());
    }

    // null jako oba argumenty
    @Test
    public void findByImieAndNazwisko5()
    {
        Autor autor1 = new Autor("Adam", "Mickiewicz");
        Autor autor2 = new Autor("Boleslaw", "Prus");
        autorRepository.save(autor1);
        autorRepository.save(autor2);
        Optional<Autor> autorOptional = autorRepository.findByImieAndNazwisko(null, null);
        assertFalse(autorOptional.isPresent());
    }
    
    // null w imieniu
    @Test
    public void findByImieAndNazwisko6()
    {
        Autor autor1 = new Autor("Adam", "Mickiewicz");
        Autor autor2 = new Autor("Boleslaw", "Prus");
        autorRepository.save(autor1);
        autorRepository.save(autor2);
        Optional<Autor> autorOptional = autorRepository.findByImieAndNazwisko(null, "Mickiewicz");
        assertTrue(autorOptional.isEmpty());
    }

    // null w nazwisku
    @Test
    public void findByImieAndNazwisko7()
    {
        Autor autor1 = new Autor("Adam", "Mickiewicz");
        Autor autor2 = new Autor("Boleslaw", "Prus");
        autorRepository.save(autor1);
        autorRepository.save(autor2);
        Optional<Autor> autorOptional = autorRepository.findByImieAndNazwisko("Adam", null);
        assertTrue(autorOptional.isEmpty());
    }

    // case sensitivity w imieniu
    @Test
    public void findByImieAndNazwisko8()
    {
        Autor autor1 = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor1);
        Optional<Autor> autorOptional = autorRepository.findByImieAndNazwisko("adam", "Mickiewicz");
        assertTrue(autorOptional.isEmpty());
    }

    // case sensitivity w nazwisku
    @Test
    public void findByImieAndNazwisko9()
    {
        Autor autor1 = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor1);
        Optional<Autor> autorOptional = autorRepository.findByImieAndNazwisko("Adam", "mickiewicz");
        assertTrue(autorOptional.isEmpty());
    }

    // pusty string w imieniu i nazwisku
    @Test
    public void findByImieAndNazwisko10()
    {
        Autor autor1 = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor1);
        Optional<Autor> autorOptional = autorRepository.findByImieAndNazwisko("", "");
        assertTrue(autorOptional.isEmpty());
    }


    // zwraca jedyny obiekt autora po ID
    @Test
    public void findByIdTest1()
    {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        Optional<Autor> autorOptional = autorRepository.findById(autor.getId());
        assertTrue(autorOptional.isPresent());
        assertEquals(autor, autorOptional.get());
    }

    // brak obiektu o danym id
    @Test
    public void findByIdTest2()
    {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        Optional<Autor> autorOptional = autorRepository.findById(autor.getId() + 1);
        assertFalse(autorOptional.isPresent());
    }

    //ID rowne NULL
    @Test
    public void findByIdTest3()
    {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> autorRepository.findById(null));
    }

    // znajdz po id ksiazki autora
    @Test
    public void findByKsiazkiIdTest1()
    {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        Ksiazka ksiazka = new Ksiazka();
        ksiazka.setTytul("Pan Tadeusz");
        ksiazka.setAutor(autor);
        ksiazkaRepository.save(ksiazka);
        autor.getKsiazki().add(ksiazka);
        Optional<Autor> autorOptional = autorRepository.findByKsiazkiId(ksiazka.getId());
        assertFalse(autorOptional.isEmpty());
        assertEquals(autor, autorOptional.get());
    }

    // sprobuj znalezc po ID ksiazki, ktorego nie ma
    @Test
    public void findByKsiazkiIdTest2()
    {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        Ksiazka ksiazka = new Ksiazka();
        ksiazka.setTytul("Pan Tadeusz");
        ksiazka.setAutor(autor);
        ksiazkaRepository.save(ksiazka);
        autor.getKsiazki().add(ksiazka);
        Optional<Autor> autorOptional = autorRepository.findByKsiazkiId(ksiazka.getId() + 1);
        assertTrue(autorOptional.isEmpty());
    }

    // id ksiazki = null
    @Test
    public void findByKsiazkiIdTest3()
    {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        Ksiazka ksiazka = new Ksiazka();
        ksiazka.setTytul("Pan Tadeusz");
        ksiazka.setAutor(autor);
        ksiazkaRepository.save(ksiazka);
        autor.getKsiazki().add(ksiazka);
        Optional<Autor> autorOptional = autorRepository.findByKsiazkiId(null);
        assertTrue(autorOptional.isEmpty());
    }

    // wiele ksiazek, test- porownanie autora z autorem ksiazki dla kadzej
    @Test
    public void findByKsiazkiIdTest4()
    {
        Autor autor = new Autor("Adam", "Mickiewicz");
        autorRepository.save(autor);
        Ksiazka ksiazka = new Ksiazka();
        ksiazka.setTytul("Pan Tadeusz");
        ksiazka.setAutor(autor);
        ksiazkaRepository.save(ksiazka);
        autor.getKsiazki().add(ksiazka);
        Ksiazka ksiazka2 = new Ksiazka();
        ksiazka2.setTytul("Sonety Krymskie");
        ksiazka2.setAutor(autor);
        ksiazkaRepository.save(ksiazka2);
        autor.getKsiazki().add(ksiazka2);
        Optional<Autor> autorOptional1 = autorRepository.findByKsiazkiId(ksiazka.getId());
        Optional<Autor> autorOptional2 = autorRepository.findByKsiazkiId(ksiazka2.getId());
        assertFalse(autorOptional1.isEmpty());
        assertFalse(autorOptional2.isEmpty());
        assertEquals(autor, autorOptional1.get());
        assertEquals(autor, autorOptional2.get());

    }









}
