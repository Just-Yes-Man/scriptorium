package com.scriptorium.scriptorium.infrastructure.repositories;

import com.scriptorium.scriptorium.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
