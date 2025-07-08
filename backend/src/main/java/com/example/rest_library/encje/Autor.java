package com.example.rest_library.encje;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Autor {

    @Id
    @GeneratedValue
    private Long id;
    private String imie;
    private String nazwisko;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ksiazka> ksiazki = new ArrayList<>();

    public Autor(String imie, String nazwisko)
    {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

}
