package com.example.rest_library.serwisy;

import com.example.rest_library.encje.Autor;
import org.springframework.stereotype.Service;
import com.example.rest_library.repo.AutorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    public List<Autor> findByNazwisko(String nazwisko)
    {
        return autorRepository.findByNazwisko(nazwisko);
    }

    public List<Autor> findByImie(String imie)
    {
        return autorRepository.findByImie(imie);
    }

    public Optional<Autor> findByImieAndNazwisko(String imie, String nazwisko) {
        return autorRepository.findByImieAndNazwisko(imie, nazwisko);
    }

    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }

    public void delete(Autor autor) {
        autorRepository.delete(autor);
    }

    public void deleteById(Long id) {
        autorRepository.deleteById(id);
    }

    public Optional<Autor> findById(Long id) {return autorRepository.findById(id);}

    public Optional<Autor> findByKsiazkiId(Long id) {return autorRepository.findByKsiazkiId(id);}

}
