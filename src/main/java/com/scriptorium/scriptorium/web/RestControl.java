package com.scriptorium.scriptorium.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scriptorium.scriptorium.domain.Bibliotecario;
import com.scriptorium.scriptorium.infrastructure.repositories.BibliotecariosRepository;

@RestController
@RequestMapping("/bibliotecarios")
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

    @GetMapping("/{id}")
    public ResponseEntity<Bibliotecario> obtenerPorId(@PathVariable Long id) {
        return repo.findById(id)
                .map(b -> ResponseEntity.ok(b))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bibliotecario> actualizarBibliotecario(@PathVariable Long id,
            @RequestBody Bibliotecario NewBibliotecario) {
        return repo.findById(id)
                .map(b -> {
                    b.setUsuario(NewBibliotecario.getUsuario());
                    b.setContraseña(NewBibliotecario.getContraseña());
                    return ResponseEntity.ok(repo.save(b));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
