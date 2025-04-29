package com.scriptorium.scriptorium.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scriptorium.scriptorium.domain.Usuario;
import com.scriptorium.scriptorium.infrastructure.repositories.UsuarioRepository;
import com.scriptorium.scriptorium.dto.UsuarioRequestDTO;
import com.scriptorium.scriptorium.dto.UsuarioResponseDTO;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<UsuarioResponseDTO> listar() {
        return repo.findAll().stream()
                .map(b -> new UsuarioResponseDTO(b.getIdUsuario(), b.getNombre(), b.getFechaNacimiento(), b.getDireccion(), b.getContacto(), b.getFotografia(), b.getClave()))
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO guardar(UsuarioRequestDTO dto) {
        Usuario nuevo = new Usuario();
        nuevo.setNombre(dto.getNombre());
        nuevo.setFechaNacimiento(dto.getFechaNacimiento());
        nuevo.setDireccion(dto.getDireccion());
        nuevo.setContacto(dto.getContacto());
        nuevo.setFotografia(dto.getFotografia());
        nuevo.setClave(dto.getClave());
        Usuario guardado = repo.save(nuevo);
        return new UsuarioResponseDTO(guardado.getIdUsuario(), guardado.getNombre(), guardado.getFechaNacimiento(), guardado.getDireccion(), guardado.getContacto(), guardado.getFotografia(), guardado.getClave());
    }

    public Optional<UsuarioResponseDTO> obtenerPorId(Long id) {
        return repo.findById(id)
                .map(b -> new UsuarioResponseDTO(b.getIdUsuario(), b.getNombre(), b.getFechaNacimiento(), b.getDireccion(), b.getContacto(), b.getFotografia(), b.getClave()));
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<UsuarioResponseDTO> actualizar(Long id, UsuarioRequestDTO dto) {
        return repo.findById(id)
                .map(b -> {
                    b.setNombre(dto.getNombre());
                    b.setClave(dto.getClave());
                    b.setDireccion(dto.getDireccion());
                    b.setContacto(dto.getContacto());
                    b.setFotografia(dto.getFotografia());
                    b.setClave(dto.getClave());
                    Usuario actualizado = repo.save(b);
                    return new UsuarioResponseDTO(actualizado.getIdUsuario(), actualizado.getNombre(), actualizado.getFechaNacimiento(), actualizado.getDireccion(), actualizado.getContacto(), actualizado.getFotografia(), actualizado.getClave());
                });
    }
}
