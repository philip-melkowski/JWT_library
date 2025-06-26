package com.example.rest_library.init;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class PythonScriptsRunner {


    private final String PYTHON = "/opt/homebrew/bin/python3"; // jeśli działa z terminala


    // klasa służy załadowaniu pustej bazy danymi książek i autorów przy pomocy skryptów .py
    // web scrraping -> Jsonl file -> POST ksiazka/autor
    // zakomentowana, bo zajmuje sporo czasu i dane juz sa dodane na baze testowa

    /* odkomentowac jesli ma sie pusta baze i chce sie ja wypelnic danymi
    @EventListener(ApplicationReadyEvent.class)
    public void runScripts() throws IOException, InterruptedException{
        String basePath = "ksiazki_py_scripts/scraper/bookscraper";

        runScript(new File(basePath, "obsluga.py"));
        runScript(new File(basePath, "load_to_db.py"));
    }
    private void runScript(File script) throws IOException, InterruptedException{
        System.out.println("Uruchamianie skryptu: " + script.getAbsolutePath());
        ProcessBuilder pb = new ProcessBuilder(PYTHON, script.getAbsolutePath());
        pb.directory(new File("ksiazki_py_scripts/scraper/bookscraper"));
        pb.inheritIO(); // pokaz stdout i stderr w konsoli SPRINGa
        Process p = pb.start();
        int code = p.waitFor();


        if(code == 0)
        {
            System.out.println("Skrypt zakoczony: " + script.getName());
        }
        else {
            System.err.println("❌ Błąd w skrypcie: " + script.getName());
        }

    }

     */


}
