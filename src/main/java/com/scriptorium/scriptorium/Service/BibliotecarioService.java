package com.scriptorium.scriptorium.Service;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scriptorium.scriptorium.domain.Bibliotecario;
import com.scriptorium.scriptorium.infrastructure.repositories.BibliotecariosRepository;
import com.scriptorium.scriptorium.dto.BibliotecarioRequestDTO;
import com.scriptorium.scriptorium.dto.BibliotecarioResponseDTO;

@Service
public class BibliotecarioService {

    private final BibliotecariosRepository repo;

    public BibliotecarioService(BibliotecariosRepository repo) {
        this.repo = repo;
    }

    public List<BibliotecarioResponseDTO> listar() {
        return repo.findAll().stream()
                .map(b -> new BibliotecarioResponseDTO(b.getIdBibliotecario(), b.getUsuario()))
                .collect(Collectors.toList());
    }

    public BibliotecarioResponseDTO guardar(BibliotecarioRequestDTO dto) {
        Bibliotecario nuevo = new Bibliotecario();
        nuevo.setUsuario(dto.getUsuario());
        nuevo.setContraseña(dto.getContraseña());
        Bibliotecario guardado = repo.save(nuevo);
        return new BibliotecarioResponseDTO(guardado.getIdBibliotecario(), guardado.getUsuario());
    }

    public Optional<BibliotecarioResponseDTO> obtenerPorId(Long id) {
        return repo.findById(id)
                .map(b -> new BibliotecarioResponseDTO(b.getIdBibliotecario(), b.getUsuario()));
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<BibliotecarioResponseDTO> actualizar(Long id, BibliotecarioRequestDTO dto) {
        return repo.findById(id)
                .map(b -> {
                    b.setUsuario(dto.getUsuario());
                    b.setContraseña(dto.getContraseña());
                    Bibliotecario actualizado = repo.save(b);
                    return new BibliotecarioResponseDTO(actualizado.getIdBibliotecario(), actualizado.getUsuario());
                });
    }
}
