package com.scriptorium.scriptorium.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.sql.Types;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import javax.sql.DataSource;

import com.scriptorium.scriptorium.domain.Bibliotecario;
import com.scriptorium.scriptorium.infrastructure.repositories.BibliotecarioRepository;
import com.scriptorium.scriptorium.dto.BibliotecarioRequestDTO;
import com.scriptorium.scriptorium.dto.BibliotecarioResponseDTO;

@Service
public class BibliotecarioService {

    private final BibliotecarioRepository repo;
    private final JdbcTemplate jdbcTemplate;

    public BibliotecarioService(BibliotecarioRepository repo, JdbcTemplate jdbcTemplate) {
        this.repo = repo;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BibliotecarioResponseDTO> listar() {
        return repo.findAll().stream()
                .map(b -> new BibliotecarioResponseDTO(b.getIdBibliotecario(), b.getUsuario()))
                .collect(Collectors.toList());
    }

    public BibliotecarioResponseDTO guardar(BibliotecarioRequestDTO dto) {

        if (verificarNombre(dto.getUsuario())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

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

    public boolean verificarNombre(String usuario) {
        String sql = "SELECT verificar_usuario(?)";

        try {

            boolean existe = jdbcTemplate.queryForObject(sql, Boolean.class, usuario);
            return existe;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public Optional<BibliotecarioResponseDTO> login(String usuario, String contraseña) {
        String sql = "SELECT * FROM login(?, ?)";

        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, usuario, contraseña);
            Long id = ((Number) result.get("id_bibliotecario")).longValue();
            String nombre = (String) result.get("usuario");

            return Optional.of(new BibliotecarioResponseDTO(id, nombre));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
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
