package com.example.rest_library.JWT_SECURITY;

import com.example.rest_library.serwisy.MojeUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MojeUserDetailsService userDetailsService;


    // filtr dziala zanim dotrze do jakiegokolwiek kontrolera przez endpoint
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try
        {
            String jwt = parseJwt(request); // pobierz token z naglowka Authentication w żądaniu
            if(jwt != null && jwtUtil.validateJwtToken(jwt)) // sprawdz czy zwrocilo token i czy jest VALID
            {
                String username = jwtUtil.getUsernameFromToken(jwt); // wyciagnij z tokenu nazwe uzyt
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);  // laduje dane uzytkownika z bazy na podstawie loginu. zwraca obiekt UserDetails

                // tworzy obiekt reprezentujacy uwierzytelnionego uzytkownika. haslo jest null, bo uwierzytelnienie juz sie odbylo
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                // dodaje do obiektu authentication szczegoly polaczenia HTTP, takie jak adres IP uzytkownika, dane o przegladarce (session ID, user agent itd.)
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // rejestruje uwierzytelnienie uzytkownika
                // po tej linii Spring traktuje uzytkownika jako zalogowanego
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        catch (Exception e)
        {
            System.out.println("Cannot set user authentication: " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request)
    {
        String headerAuth = request.getHeader("Authorization");
        if(headerAuth != null && headerAuth.startsWith("Bearer "))
        {
            return headerAuth.substring(7); // tutaj substr 7 bo header jest w postaci: Bearer klucz, wiec pomijamy Bearer i spacje
        }
        return null;
    }
}
