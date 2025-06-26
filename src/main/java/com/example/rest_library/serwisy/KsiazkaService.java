package com.example.rest_library.serwisy;

import com.example.rest_library.encje.Autor;
import com.example.rest_library.encje.Ksiazka;
import org.springframework.stereotype.Service;
import com.example.rest_library.repo.KsiazkaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class KsiazkaService {
    private final KsiazkaRepository ksiazkaRepository;

    public KsiazkaService(KsiazkaRepository ksiazkaRepository) {
        this.ksiazkaRepository = ksiazkaRepository;
    }

    public Optional<Ksiazka> findById(long id) {
        return ksiazkaRepository.findById(id);
    }

    public List<Ksiazka> findByAutor(Autor autor) {
        return ksiazkaRepository.findByAutor(autor);
    }

    public List<Ksiazka> findByAutorId(long id) {
        return ksiazkaRepository.findByAutorId(id);
    }

    public Optional<Ksiazka> findByTytul(String tytul) {
        return ksiazkaRepository.findByTytul(tytul);
    }

    /*
    public List<Ksiazka> findByRokWydania(int rok) {
        return ksiazkaRepository.findByRokWydania(rok);
    }
    */

    /*
    public List<Ksiazka> findByRokWydaniaBetween(int start, int koniecRok) {
        return ksiazkaRepository.findByRokWydaniaBetween(start, koniecRok);
    }

     */

    public void deleteByAutor(Autor autor) {
        ksiazkaRepository.deleteByAutor(autor);
    }

    public void deleteByAutorId(long id) {
        ksiazkaRepository.deleteByAutorId(id);
    }

    public List<Ksiazka> findByTytulContainingIgnoreCase(String fragmentTytul) {
        return ksiazkaRepository.findByTytulContainingIgnoreCase(fragmentTytul);
    }

    public void deleteById(Long id) {
        ksiazkaRepository.deleteById(id);
    }

    public List<Ksiazka> findAll()
    {
        return ksiazkaRepository.findAll();
    }

    public Ksiazka save(Ksiazka ksiazka) {
        return ksiazkaRepository.save(ksiazka);
    }

    public Optional<Ksiazka> findById(Long id) { return ksiazkaRepository.findById(id);}

    public List<Ksiazka> findByUzytkownikUsernameAndPrzeczytaneFalse(String username) { return ksiazkaRepository.findByUzytkownikUsernameAndPrzeczytaneFalse(username);}

    public boolean existsByTytulAndAutorId(String tytul, long autorId) { return ksiazkaRepository.existsByTytulAndAutorId(tytul, autorId);}

    public Double sredniaOcenKsiazkiById(Long id) { return ksiazkaRepository.sredniaOcenKsiazkiById(id);}
}
