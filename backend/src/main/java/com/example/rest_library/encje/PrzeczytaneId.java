package com.example.rest_library.encje;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@RequiredArgsConstructor
public class PrzeczytaneId implements Serializable {
    private Long ksiazka;
    private Long uzytkownik;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrzeczytaneId)) return false;
        PrzeczytaneId that = (PrzeczytaneId) o;
        return Objects.equals(uzytkownik, that.uzytkownik) && Objects.equals(ksiazka, that.ksiazka);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ksiazka, uzytkownik);
    }
}
