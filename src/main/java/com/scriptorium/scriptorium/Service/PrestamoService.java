package com.scriptorium.scriptorium.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scriptorium.scriptorium.domain.Prestamo;
import com.scriptorium.scriptorium.domain.Usuario;
import com.scriptorium.scriptorium.domain.Libro;
import com.scriptorium.scriptorium.domain.Bibliotecario;
import com.scriptorium.scriptorium.infrastructure.repositories.PrestamoRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.UsuarioRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.LibroRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.BibliotecarioRepository;
import com.scriptorium.scriptorium.dto.PrestamoRequestDTO;
import com.scriptorium.scriptorium.dto.PrestamoResponseDTO;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepo;
    private final UsuarioRepository usuarioRepo;
    private final LibroRepository libroRepo;
    private final BibliotecarioRepository bibliotecarioRepo;

    public PrestamoService(PrestamoRepository prestamoRepo, UsuarioRepository usuarioRepo, 
                            LibroRepository libroRepo, BibliotecarioRepository bibliotecarioRepo) {
        this.prestamoRepo = prestamoRepo;
        this.usuarioRepo = usuarioRepo;
        this.libroRepo = libroRepo;
        this.bibliotecarioRepo = bibliotecarioRepo;
    }

    public List<PrestamoResponseDTO> listar() {
        return prestamoRepo.findAll().stream()
                .map(p -> new PrestamoResponseDTO(
                        p.getIdPrestamo(),
                        p.getFicha(),
                        p.getUsuario() != null ? p.getUsuario().getIdUsuario() : 0,
                        p.getLibro() != null ? p.getLibro().getIdLibro() : 0,
                        p.getBibliotecario() != null ? p.getBibliotecario().getIdBibliotecario() : 0,
                        p.isActivo(),
                        p.isMultado(),
                        p.getEstadoPrestamo(),
                        p.getEstadoDevuelto(),
                        p.getFechaInicio(),
                        p.getFechaFin()
                ))
                .collect(Collectors.toList());
    }

    public PrestamoResponseDTO guardar(PrestamoRequestDTO dto) {
        Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Libro libro = libroRepo.findById(dto.getLibroId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        Bibliotecario bibliotecario = bibliotecarioRepo.findById(dto.getBibliotecarioId())
                .orElseThrow(() -> new RuntimeException("Bibliotecario no encontrado"));

        Prestamo nuevo = new Prestamo();
        nuevo.setFicha(dto.getFicha());
        nuevo.setUsuario(usuario);
        nuevo.setLibro(libro);
        nuevo.setBibliotecario(bibliotecario);
        nuevo.setActivo(dto.isActivo());
        nuevo.setMultado(dto.isMultado());
        nuevo.setEstadoPrestamo(dto.getEstadoPrestamo());
        nuevo.setEstadoDevuelto(dto.getEstadoDevuelto());
        nuevo.setFechaInicio(dto.getFechaInicio());
        nuevo.setFechaFin(dto.getFechaFin());

        Prestamo guardado = prestamoRepo.save(nuevo);

        return new PrestamoResponseDTO(
                guardado.getIdPrestamo(),
                guardado.getFicha(),
                guardado.getUsuario() != null ? guardado.getUsuario().getIdUsuario() : 0,
                guardado.getLibro() != null ? guardado.getLibro().getIdLibro() : 0,
                guardado.getBibliotecario() != null ? guardado.getBibliotecario().getIdBibliotecario() : 0,
                guardado.isActivo(),
                guardado.isMultado(),
                guardado.getEstadoPrestamo(),
                guardado.getEstadoDevuelto(),
                guardado.getFechaInicio(),
                guardado.getFechaFin()
        );
    }

    public Optional<PrestamoResponseDTO> obtenerPorId(Long id) {
        return prestamoRepo.findById(id)
                .map(p -> new PrestamoResponseDTO(
                        p.getIdPrestamo(),
                        p.getFicha(),
                        p.getUsuario() != null ? p.getUsuario().getIdUsuario() : 0,
                        p.getLibro() != null ? p.getLibro().getIdLibro() : 0,
                        p.getBibliotecario() != null ? p.getBibliotecario().getIdBibliotecario() : 0,
                        p.isActivo(),
                        p.isMultado(),
                        p.getEstadoPrestamo(),
                        p.getEstadoDevuelto(),
                        p.getFechaInicio(),
                        p.getFechaFin()
                ));
    }

    public boolean eliminar(Long id) {
        if (prestamoRepo.existsById(id)) {
            prestamoRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<PrestamoResponseDTO> actualizar(Long id, PrestamoRequestDTO dto) {
        return prestamoRepo.findById(id)
                .map(p -> {
                    Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                    Libro libro = libroRepo.findById(dto.getLibroId())
                            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
                    Bibliotecario bibliotecario = bibliotecarioRepo.findById(dto.getBibliotecarioId())
                            .orElseThrow(() -> new RuntimeException("Bibliotecario no encontrado"));

                    p.setFicha(dto.getFicha());
                    p.setUsuario(usuario);
                    p.setLibro(libro);
                    p.setBibliotecario(bibliotecario);
                    p.setActivo(dto.isActivo());
                    p.setMultado(dto.isMultado());
                    p.setEstadoPrestamo(dto.getEstadoPrestamo());
                    p.setEstadoDevuelto(dto.getEstadoDevuelto());
                    p.setFechaInicio(dto.getFechaInicio());
                    p.setFechaFin(dto.getFechaFin());

                    Prestamo actualizado = prestamoRepo.save(p);

                    return new PrestamoResponseDTO(
                            actualizado.getIdPrestamo(),
                            actualizado.getFicha(),
                            actualizado.getUsuario() != null ? actualizado.getUsuario().getIdUsuario() : 0,
                            actualizado.getLibro() != null ? actualizado.getLibro().getIdLibro() : 0,
                            actualizado.getBibliotecario() != null ? actualizado.getBibliotecario().getIdBibliotecario() : 0,
                            actualizado.isActivo(),
                            actualizado.isMultado(),
                            actualizado.getEstadoPrestamo(),
                            actualizado.getEstadoDevuelto(),
                            actualizado.getFechaInicio(),
                            actualizado.getFechaFin()
                    );
                });
    }
}
