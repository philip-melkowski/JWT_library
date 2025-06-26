package com.example.rest_library.controllery;

import com.example.rest_library.DTO.AutorDTO;
import com.example.rest_library.encje.Autor;
import com.example.rest_library.encje.Przeczytane;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.rest_library.serwisy.AutorService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/autorzy")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    // 1. zwroc wszystkich autorow
    @GetMapping
    public List<AutorDTO> findAll()
    {
        return autorService.findAll().stream().map(AutorDTO::new).collect(Collectors.toList());
    }

    // 2. znajdz autorow po nazwisku
    @GetMapping("/szukajByNazwisko")
    public List<AutorDTO> findByNazwisko(@RequestParam String nazwisko)
    {

        return autorService.findByNazwisko(nazwisko).stream().map(AutorDTO::new).collect(Collectors.toList());
    }

    // 3. znajdz autorow po imieniu
    @GetMapping("/szukajByImie")
    public List<AutorDTO> findByImie(@RequestParam String imie)
    {
        return autorService.findByImie(imie).stream().map(AutorDTO::new).collect(Collectors.toList());
    }

    // 4. znajdz autora po imieniu i nazwisku - zakladamy ze nie ma powtorek
    @GetMapping("szukajByImieNazwisko")
    public ResponseEntity<AutorDTO> findByImieAndNazwisko(@RequestParam String imie, @RequestParam String nazwisko)
    {
        return autorService.findByImieAndNazwisko(imie, nazwisko).map(autor -> ResponseEntity.ok(new AutorDTO(autor))).orElse(ResponseEntity.notFound().build());
    }

    // 5. dodaj autora
    @PostMapping
    public ResponseEntity<Autor> addAutor(@RequestBody Autor autor)
    {
        if(autorService.findByImieAndNazwisko(autor.getImie(), autor.getNazwisko()).isPresent())
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Autor savedAutor = autorService.save(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAutor);
    }

    /*
    // 6. dodaj autora i zwroc jego adres
    @PostMapping("/dodaj")
    public ResponseEntity<Autor> addAutor2(@RequestBody Autor autor)
    {
        Autor savedAutor = autorService.save(autor);
        URI location = URI.create("/api/autorzy/" + savedAutor.getId());
        return ResponseEntity.created(location).body(savedAutor);
    }

     */

    // 7. usuna autora na podstawie ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id)
    {
        autorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 8. pokaz autora na podstawie ID
    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> findById(@PathVariable Long id)
    {
        Optional<Autor> autor = autorService.findById(id);
        return autor.map(a -> ResponseEntity.ok(new AutorDTO(a))).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/autorKsiazki")
    public ResponseEntity<AutorDTO> findByKsiazkaId(@RequestParam Long id)
    {
        Optional<Autor> autor = autorService.findByKsiazkiId(id);
        return autor.map(a -> ResponseEntity.ok(new AutorDTO(a))).orElse(ResponseEntity.notFound().build());
    }
}
