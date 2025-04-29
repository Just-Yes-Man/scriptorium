package com.scriptorium.scriptorium.infrastructure.repositories;

import com.scriptorium.scriptorium.domain.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

}
