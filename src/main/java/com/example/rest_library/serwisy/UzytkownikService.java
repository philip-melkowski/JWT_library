package com.example.rest_library.serwisy;

import com.example.rest_library.encje.Uzytkownik;
import com.example.rest_library.repo.UzytkownikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UzytkownikService {
    private final UzytkownikRepository uzytkownikRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Uzytkownik> findById(Long id)
    {
        return uzytkownikRepository.findById(id);
    };
    public Optional<Uzytkownik> findByUsername(String username)
    {
        return uzytkownikRepository.findByUsername(username);
    };
    public Optional<Uzytkownik> findByImieAndNazwisko(String imie, String nazwisko)
    {
        return uzytkownikRepository.findByImieAndNazwisko(imie, nazwisko);
    };
    public Uzytkownik save(Uzytkownik uzytkownik)
    {
        String zakodowaneHaslo = passwordEncoder.encode(uzytkownik.getPassword());
        uzytkownik.setPassword(zakodowaneHaslo);
        return uzytkownikRepository.save(uzytkownik);
    }
    public List<Uzytkownik> findAll() { return uzytkownikRepository.findAll();}

    public boolean existsByUsername(String username) { return uzytkownikRepository.findByUsername(username).isPresent();}
}
