package com.example.rest_library.encje;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Autor {

    @Id
    @GeneratedValue
    private Long id;
    private String imie;
    private String nazwisko;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ksiazka> ksiazki;


}
