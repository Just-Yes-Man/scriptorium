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

import com.scriptorium.scriptorium.Service.TipoMultaService;
import com.scriptorium.scriptorium.dto.TipoMultaRequestDTO;
import com.scriptorium.scriptorium.dto.TipoMultaResponseDTO;

@RestController
@RequestMapping("/TipoMulta")
public class TipoMultaControl {

    private final TipoMultaService service;

    public TipoMultaControl(TipoMultaService service) {
        this.service = service;
    }

    @GetMapping
    public List<TipoMultaResponseDTO> listar() {
        return service.listar();
    }

    @PostMapping
    public TipoMultaResponseDTO guardar(@RequestBody TipoMultaRequestDTO dto) {
        return service.guardar(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoMultaResponseDTO> obtenerPorId(@PathVariable Long id) {
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
    public ResponseEntity<TipoMultaResponseDTO> actualizar(@PathVariable Long id,
            @RequestBody TipoMultaRequestDTO dto) {
        return service.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
