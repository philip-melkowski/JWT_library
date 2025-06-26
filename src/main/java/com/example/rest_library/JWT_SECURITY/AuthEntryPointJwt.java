package com.example.rest_library.JWT_SECURITY;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// klasa ta definiuje co ma sie dziac, gdy niezalogowany uzytkownik probuje uzyskac dostep do zasobów, które wymagają zalogowania
// NIE OBSLUGUJE BRAKU UPRAWNIEN PRZY BYCIU ZALOGOWANYM
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}
