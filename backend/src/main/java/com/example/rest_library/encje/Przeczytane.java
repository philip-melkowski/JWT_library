package com.example.rest_library.encje;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@IdClass(PrzeczytaneId.class)
public class Przeczytane {

    @Id
    @ManyToOne
    @JoinColumn(name = "ksiazka_id", nullable = false)
    private Ksiazka ksiazka;

    @Id
    @ManyToOne
    @JoinColumn(name = "uzytkownik_id", nullable = false)
    private Uzytkownik uzytkownik;

    private Integer ocena;
}
