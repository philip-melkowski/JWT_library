package com.example.rest_library.serwisy;

import com.example.rest_library.encje.Ksiazka;
import com.example.rest_library.encje.Przeczytane;
import com.example.rest_library.encje.Uzytkownik;
import com.example.rest_library.repo.PrzeczytaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrzeczytaneService {
    private final PrzeczytaneRepository przeczytaneRepository;

    public List<Przeczytane> findAll() { return przeczytaneRepository.findAll(); }
    public List<Przeczytane> findByUzytkownik(Uzytkownik uzytkownik) { return przeczytaneRepository.findByUzytkownik(uzytkownik); }
    public List<Przeczytane> findByUzytkownikId(Long id) { return przeczytaneRepository.findByUzytkownikId(id); }
    public List<Przeczytane> findByUzytkownikUsername(String uzytkownikUsername) { return przeczytaneRepository.findByUzytkownikUsername(uzytkownikUsername); }
    public List<Przeczytane> findByKsiazkaId(Long id) { return przeczytaneRepository.findByKsiazkaId(id); }
    public List<Przeczytane> findByKsiazka(Ksiazka ksiazka) { return przeczytaneRepository.findByKsiazka(ksiazka); }
    public Optional<Przeczytane> findByUzytkownikIdAndKsiazkaId(Long uzytkownikId, Long ksiazkaId) { return przeczytaneRepository.findByUzytkownikIdAndKsiazkaId(uzytkownikId, ksiazkaId); }
    public Double sredniaOcenKsiazki(Ksiazka ksiazka)
    {
        List<Przeczytane> przeczytaneList = findByKsiazka(ksiazka);
        Double sumaOcen = 0.0;
        for(Przeczytane przeczytane : przeczytaneList)
        {
            sumaOcen += przeczytane.getOcena();
        }
        if(przeczytaneList.size() == 0)
            return 0.0;
        return sumaOcen / przeczytaneList.size();
    }
    public Double sredniaOcenKsiazkiById(Long id)
    {
        List<Przeczytane> przeczytaneList = findByKsiazkaId(id);
        Double sumaOcen = 0.0;
        if(przeczytaneList.isEmpty())
            return 0.0;
        for(Przeczytane przeczytane : przeczytaneList)
        {
            sumaOcen += przeczytane.getOcena();
        }
        return sumaOcen / przeczytaneList.size();
    }
    public Double sredniaByUzytkownik(Uzytkownik uzytkownik)
    {
        List<Przeczytane> przeczytaneList = findByUzytkownik(uzytkownik);
        Double sumaOcen = 0.0;
        if(przeczytaneList.isEmpty())
            return 0.0;
        for(Przeczytane przeczytane : przeczytaneList)
        {
            sumaOcen += przeczytane.getOcena();
        }

        return sumaOcen / przeczytaneList.size();
    }
    public Double sredniaByUzytkownikId(Long id)
    {
        List<Przeczytane> przeczytaneList = findByUzytkownikId(id);
        Double sumaOcen = 0.0;

        if(przeczytaneList.isEmpty())
            return 0.0;
        for(Przeczytane przeczytane : przeczytaneList)
        {
            sumaOcen += przeczytane.getOcena();
        }
        return sumaOcen / przeczytaneList.size();
    }

    public boolean czyUzytkownikPrzeczytalKsiazke(Long uzytkownikId, Long ksiazkaId)
    {
        return przeczytaneRepository.existsByUzytkownikIdAndKsiazkaId(uzytkownikId, ksiazkaId);
    }

    public Przeczytane save(Przeczytane przeczytane)
    {
        return przeczytaneRepository.save(przeczytane);
    }

    public void delete(Przeczytane przeczytane)
    {
        przeczytaneRepository.delete(przeczytane);
    }




}
