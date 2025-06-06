package com.scriptorium.scriptorium.infrastructure.repositories;

import com.scriptorium.scriptorium.domain.Usuario;
import com.scriptorium.scriptorium.dto.UsuarioResponseDTO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query(value = "SELECT * FROM buscar_usuarios(:palabra)", nativeQuery = true)
    List<Object[]> buscarUsuarios(@Param("palabra") String palabra);
}
