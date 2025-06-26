package com.example.rest_library.DTO;

import com.example.rest_library.encje.Przeczytane;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class PrzeczytaneDTO {
    private Long uzytkownikId;
    private String uzytkownikUsername;
    private Long ksiazkaId;
    private String ksiazkaTytul;
    private Integer ocena;

    public PrzeczytaneDTO(Przeczytane przeczytane)
    {
        this.uzytkownikId = przeczytane.getUzytkownik().getId();
        this.uzytkownikUsername = przeczytane.getUzytkownik().getUsername();
        this.ksiazkaId = przeczytane.getKsiazka().getId();
        this.ksiazkaTytul = przeczytane.getKsiazka().getTytul();
        this.ocena = przeczytane.getOcena();
    }




}
