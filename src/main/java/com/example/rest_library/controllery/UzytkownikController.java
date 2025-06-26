package com.example.rest_library.controllery;

import com.example.rest_library.DTO.LoginRequestDTO;
import com.example.rest_library.encje.Uzytkownik;
import com.example.rest_library.serwisy.UzytkownikService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/uzytkownicy")
@CrossOrigin(origins = "*")
public class UzytkownikController {
    private final UzytkownikService uzytkownikService;

    @Autowired
    private AuthenticationManager authenticationManager;


    // 1. dodaj uzytkownika
    @PostMapping
    public ResponseEntity<?> addUzytkownik(@RequestBody Uzytkownik uzytkownik)
    {

        if(uzytkownikService.existsByUsername(uzytkownik.getUsername()))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("nazwa użytkownika zajęta");
        }
        Uzytkownik savedUzytkownik = uzytkownik;
        savedUzytkownik.setPassword(uzytkownik.getPassword());
        uzytkownikService.save(savedUzytkownik);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUzytkownik);
    }

    /*
    // 2. dodaj uzytkownika i zwroc jego adres
    @PostMapping("/dodaj")
    public ResponseEntity<?> addUzytkownik2(@RequestBody Uzytkownik uzytkownik)
    {
        if(uzytkownikService.existsByUsername(uzytkownik.getUsername()))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("nazwa użytkownika zajęta");
        }
        Uzytkownik savedUzytkownik = uzytkownikService.save(uzytkownik);
        URI location = URI.create("/api/uzytkownicy/" + savedUzytkownik.getId());
        return ResponseEntity.created(location).body(savedUzytkownik);
    }

     */


    // 3. znajdz po ID
    @GetMapping("/{id}")
    public ResponseEntity<Uzytkownik> findById(@PathVariable Long id)
    {
        return uzytkownikService.findById(id).map(u -> ResponseEntity.ok(u)).orElse(ResponseEntity.notFound().build());
    }

    // 4, znajdz po nazwie uzytkownika
    @GetMapping("/findByUsername")
    public ResponseEntity<Uzytkownik> findByUsername(@RequestParam String username)
    {
        return uzytkownikService.findByUsername(username).map(u -> ResponseEntity.ok(u)).orElse(ResponseEntity.notFound().build());
    }

    // 5. wszyscy uzytkownicy
    @GetMapping
    public List<Uzytkownik> findAll()
    {
        return uzytkownikService.findAll();
    }


    
    // 6. logowanie - jednak zbedne - spring security domyślnie dodaje endpoint post /login, do logowania
    /*
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest)
    {
        try
        {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("zalogowano pomyślnie");

        }
        catch(BadCredentialsException e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Zły login lub haslo!");
        }

    }
    */


    // 7. sprawdza jaki user jest zalgowowany
    @GetMapping("/ktoZalogowany")
    public ResponseEntity<String> ktoZalogowany()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.isAuthenticated())
        {
            return ResponseEntity.ok(authentication.getName());
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nie zalogowano!");
        }
    }

    // 8. zwraca ID zalogowanego uzytkownika
    @GetMapping("/getUserId")
    public ResponseEntity<?> getUserId()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.isAuthenticated())
        {
            return ResponseEntity.ok(uzytkownikService.findByUsername(authentication.getName()).get().getId());
        }

        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nie zalogowano!");
        }
    }

    // 9. zwraca role zalogowanego uzytkownika
    @GetMapping("/rola")
    public ResponseEntity<String> rolaZalogowanego()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.isAuthenticated())
        {
            return uzytkownikService.findByUsername(auth.getName())
                    .map(u -> ResponseEntity.ok(u.getRola()))
                    .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("brak"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("brak");
    }




}
