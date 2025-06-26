package com.example.rest_library.controllery;


import com.example.rest_library.DTO.LoginRequestDTO;
import com.example.rest_library.JWT_SECURITY.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRegisterController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        // uwierzytelnianie uzytkownika
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );

        // ustawienie uzytkownika jako zalogowanego w kontekscie Spring Security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // generuje token
        String jwt = jwtUtil.generateToken(((UserDetails) authentication.getPrincipal()).getUsername());

        // zwroc token
        return ResponseEntity.ok(jwt);
    }
}
