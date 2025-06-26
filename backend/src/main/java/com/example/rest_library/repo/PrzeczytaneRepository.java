package com.example.rest_library.repo;

import com.example.rest_library.DTO.PrzeczytaneDTO;
import com.example.rest_library.encje.Ksiazka;
import com.example.rest_library.encje.Przeczytane;
import com.example.rest_library.encje.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrzeczytaneRepository extends JpaRepository<Przeczytane, Long> {

     List<Przeczytane> findAll();

     List<Przeczytane> findByUzytkownik(Uzytkownik uzytkownik);

     List<Przeczytane> findByUzytkownikId(Long id);

     List<Przeczytane> findByUzytkownikUsername(String uzytkownikUsername);

     List<Przeczytane> findByKsiazkaId(Long id);

     List<Przeczytane> findByKsiazka(Ksiazka ksiazka);

     Optional<Przeczytane> findByUzytkownikAndKsiazkaId(Uzytkownik uzytkownik, Long id);

     Optional<Przeczytane> findByUzytkownikIdAndKsiazkaId(Long uzytkownik_id, Long ksiazka_id);

     boolean existsByUzytkownikAndKsiazka(Uzytkownik uzytkownik, Ksiazka ksiazka);

     boolean existsByUzytkownikIdAndKsiazkaId(Long uzytkownik_id, Long ksiazka_id);


}
