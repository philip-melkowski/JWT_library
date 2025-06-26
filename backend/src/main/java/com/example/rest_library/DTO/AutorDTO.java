package com.example.rest_library.DTO;

import com.example.rest_library.encje.Autor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutorDTO {
    private Long id;
    private String imie;
    private String nazwisko;

    public AutorDTO(Autor autor)
    {
        id = autor.getId();
        imie = autor.getImie();
        nazwisko = autor.getNazwisko();
    }
}
