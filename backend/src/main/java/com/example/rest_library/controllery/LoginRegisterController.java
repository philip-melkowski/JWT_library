package com.example.rest_library.controllery;


import com.example.rest_library.DTO.LoginRequestDTO;
import com.example.rest_library.JWT_SECURITY.JwtUtil;
import com.example.rest_library.encje.Uzytkownik;
import com.example.rest_library.serwisy.UzytkownikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoginRegisterController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UzytkownikService uzytkownikService;

    @Autowired
    private JwtUtil jwtUtil;

    // logowanie
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        // uwierzytelnianie uzytkownika
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );
        List<String> roles = authentication.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList()); // role


        // ustawienie uzytkownika jako zalogowanego w kontekscie Spring Security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // generuje token
        String jwt = jwtUtil.generateToken(((UserDetails) authentication.getPrincipal()).getUsername(), roles);

        // zwroc token
        return ResponseEntity.ok(jwt);
    }

    // rejestracja
    @PostMapping("/api/auth/register")
    public ResponseEntity<String> register(@RequestBody Uzytkownik uzytkownik) {
        if(uzytkownikService.existsByUsername(uzytkownik.getUsername()))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("istnieje juz taki uzytkownik");
        }
        if(uzytkownik.getRola() == null)
            uzytkownik.setRola("ROLE_USER");
        uzytkownikService.save(uzytkownik);
        return ResponseEntity.status(HttpStatus.CREATED).body("konto zostalo stworzone");
    }
}
