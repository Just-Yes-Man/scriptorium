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

import com.scriptorium.scriptorium.Service.PrestamoService;
import com.scriptorium.scriptorium.dto.PrestamoRequestDTO;
import com.scriptorium.scriptorium.dto.PrestamoResponseDTO;

@RestController
@RequestMapping("/Prestamo")
public class PrestamoControl {

    private final PrestamoService service;

    public PrestamoControl(PrestamoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PrestamoResponseDTO> listar() {
        return service.listar();
    }

    @PostMapping
    public PrestamoResponseDTO guardar(@RequestBody PrestamoRequestDTO dto) {
        return service.guardar(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> obtenerPorId(@PathVariable Long id) {
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
    public ResponseEntity<PrestamoResponseDTO> actualizar(@PathVariable Long id,
            @RequestBody PrestamoRequestDTO dto) {
        return service.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/devolver/{id}")
    public ResponseEntity<PrestamoResponseDTO> devolverLibro(@PathVariable Long id) {
        try {
            PrestamoResponseDTO dto = service.devolverLibro(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
