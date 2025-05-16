package com.scriptorium.scriptorium.web;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scriptorium.scriptorium.Service.BibliotecarioService;
import com.scriptorium.scriptorium.dto.BibliotecarioRequestDTO;
import com.scriptorium.scriptorium.dto.BibliotecarioResponseDTO;

@RestController
@RequestMapping("/bibliotecarios")
public class BibliotecaControl {

    private final BibliotecarioService service;

    public BibliotecaControl(BibliotecarioService service) {
        this.service = service;
    }

    @GetMapping
    public Page<BibliotecarioResponseDTO> listarBibliotecarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.listar(pageable);
    }

    @PostMapping
    public BibliotecarioResponseDTO guardar(@RequestBody BibliotecarioRequestDTO dto) {
        return service.guardar(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BibliotecarioResponseDTO> obtenerPorId(@PathVariable Long id) {
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
    public ResponseEntity<BibliotecarioResponseDTO> actualizar(@PathVariable Long id,
            @RequestBody BibliotecarioRequestDTO dto) {
        return service.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/verificar-nombre")
    public ResponseEntity<?> verificarNombre(@RequestParam String nombre) {

        boolean existe = service.verificarNombre(nombre);

        if (existe) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensaje", "El nombre de usuario ya está en uso"));
        } else {
            return ResponseEntity.ok(Map.of("mensaje", "El nombre de usuario está disponible"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String usuario = credenciales.get("usuario");
        String contraseña = credenciales.get("contraseña");

        return service.login(usuario, contraseña)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas"));
    }

}
