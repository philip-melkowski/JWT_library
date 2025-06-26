package com.example.rest_library.serwisy;

import com.example.rest_library.encje.Uzytkownik;
import com.example.rest_library.repo.UzytkownikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MojeUserDetailsService implements UserDetailsService {

    private final UzytkownikRepository uzytkownikRepository;

    public MojeUserDetailsService(UzytkownikRepository uzytkownikRepository) {
        this.uzytkownikRepository = uzytkownikRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Uzytkownik uzytkownik = uzytkownikRepository.findByUsername(username)
                .orElseThrow(
                () ->
                new UsernameNotFoundException("Nie znaleziono!")
                );
            return new org.springframework.security.core.userdetails.User(uzytkownik.getUsername(), uzytkownik.getPassword(), List.of(new SimpleGrantedAuthority(uzytkownik.getRola())));


    }
}
