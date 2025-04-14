package com.scriptorium.scriptorium.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scriptorium.scriptorium.domain.Bibliotecario;
import com.scriptorium.scriptorium.infrastructure.repositories.BibliotecariosRepository;

@RestController
@RequestMapping("/entidades")
public class RestControl {

    private final BibliotecariosRepository repo;

    public RestControl(BibliotecariosRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Bibliotecario> listar() {
        return repo.findAll();
    }

    @PostMapping
    public Bibliotecario guardar(@RequestBody Bibliotecario ent) {
        return repo.save(ent);
    }
}
