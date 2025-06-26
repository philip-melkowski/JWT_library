package com.example.rest_library.repo;

import com.example.rest_library.encje.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Long>
{
    List<Uzytkownik> findAll();
    Optional<Uzytkownik> findById(Long id);
    Optional<Uzytkownik> findByUsername(String username);
    Optional<Uzytkownik> findByImieAndNazwisko(String imie, String nazwisko);
    boolean existsUzytkownikByUsername(String username);
}
