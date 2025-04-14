package com.scriptorium.scriptorium.infrastructure.repositories;

import com.scriptorium.scriptorium.domain.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliotecariosRepository extends JpaRepository<Bibliotecario, Long> {

}
