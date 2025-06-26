package com.example.rest_library.repo;

import com.example.rest_library.encje.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long>{

    List<Autor> findByNazwisko(String nazwisko);

    List<Autor> findByImie(String imie);

    Optional<Autor> findByImieAndNazwisko(String imie, String nazwisko);

    Optional<Autor> findById(Long id);

    Optional<Autor> findByKsiazkiId(Long id);

}
