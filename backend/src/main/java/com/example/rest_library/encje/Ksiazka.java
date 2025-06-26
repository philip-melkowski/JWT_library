package com.example.rest_library.encje;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ksiazka {

    @Id
    @GeneratedValue
    private Long id;
    private String tytul;
    //@Column(name="rokwydania")
    //private int rokWydania;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autorid", nullable = false)
    Autor autor;
}
