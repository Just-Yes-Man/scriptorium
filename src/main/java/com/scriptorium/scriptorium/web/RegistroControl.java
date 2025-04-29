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

import com.scriptorium.scriptorium.Service.RegistroService;
import com.scriptorium.scriptorium.dto.RegistroRequestDTO;
import com.scriptorium.scriptorium.dto.RegistroResponseDTO;

@RestController
@RequestMapping("/Registro")
public class RegistroControl {

    private final RegistroService service;

    public RegistroControl(RegistroService service) {
        this.service = service;
    }

    @GetMapping
    public List<RegistroResponseDTO> listar() {
        return service.listar();
    }

    @PostMapping
    public RegistroResponseDTO guardar(@RequestBody RegistroRequestDTO dto) {
        return service.guardar(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroResponseDTO> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroResponseDTO> actualizar(@PathVariable Long id,
            @RequestBody RegistroRequestDTO dto) {
        return service.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
