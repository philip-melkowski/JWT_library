package com.example.rest_library.repo;

import com.example.rest_library.encje.Autor;
import com.example.rest_library.encje.Ksiazka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KsiazkaRepository extends JpaRepository<Ksiazka, Long> {


    List<Ksiazka> findByAutor(Autor autor);

    List<Ksiazka> findByAutorId(long id);

    Optional<Ksiazka> findByTytul(String tytul);

    //List<Ksiazka> findByRokWydania(int rok);

    //List<Ksiazka> findByRokWydaniaBetween(int startRok, int koniecRok);

    void deleteByAutor(Autor autor);

    void deleteByAutorId(long id);

    List<Ksiazka> findByTytulContainingIgnoreCase(String fragmentTytulu);

    void deleteById(long id);

    Optional<Ksiazka> findById(long id);

    boolean existsByTytulAndAutorId(String tytul, long autorId);

    // zwraca nieprzeczytane przez uzytkownika ksiazki
    @Query("select k from Ksiazka k where NOT EXISTS (select p from Przeczytane p where p.ksiazka = k and p.uzytkownik.username = :username)")
    List<Ksiazka> findByUzytkownikUsernameAndPrzeczytaneFalse(@Param("username") String username);

    // zwraca srednia ocen dla ksiazki
    @Query("select avg(p.ocena) from Przeczytane p where p.ksiazka.id = :id")
    Double sredniaOcenKsiazkiById(@Param("id") Long id);
}
