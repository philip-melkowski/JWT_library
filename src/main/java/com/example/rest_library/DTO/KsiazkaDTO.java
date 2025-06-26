package com.example.rest_library.DTO;

import com.example.rest_library.encje.Ksiazka;
import lombok.Getter;

@Getter
public class KsiazkaDTO {
    Long id;
    String tytul;
    //int rokWydania;
    String imieAutora;
    String nazwiskoAutora;
    Double sredniaOcen;


    public KsiazkaDTO(Ksiazka ksiazka)
    {
        this(ksiazka, null);
    }

    public KsiazkaDTO(Ksiazka ksiazka, Double sredniaOcen)
    {
        id = ksiazka.getId();
        tytul = ksiazka.getTytul();
        //rokWydania = ksiazka.getRokWydania();
        imieAutora = ksiazka.getAutor().getImie();
        nazwiskoAutora = ksiazka.getAutor().getNazwisko();
        this.sredniaOcen = sredniaOcen;

    }
}
