package com.scriptorium.scriptorium.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scriptorium.scriptorium.domain.Prestamo;
import com.scriptorium.scriptorium.domain.Usuario;
import com.scriptorium.scriptorium.domain.Libro;
import com.scriptorium.scriptorium.domain.Bibliotecario;
import com.scriptorium.scriptorium.domain.Inventario;
import com.scriptorium.scriptorium.infrastructure.repositories.PrestamoRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.UsuarioRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.LibroRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.BibliotecarioRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.InventarioRepository;
import com.scriptorium.scriptorium.dto.PrestamoRequestDTO;
import com.scriptorium.scriptorium.dto.PrestamoResponseDTO;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepo;
    private final UsuarioRepository usuarioRepo;
    private final LibroRepository libroRepo;
    private final BibliotecarioRepository bibliotecarioRepo;
    private final InventarioRepository inventarioRepo;

    public PrestamoService(PrestamoRepository prestamoRepo, UsuarioRepository usuarioRepo, 
                            LibroRepository libroRepo, BibliotecarioRepository bibliotecarioRepo, InventarioRepository inventarioRepo) {
        this.prestamoRepo = prestamoRepo;
        this.usuarioRepo = usuarioRepo;
        this.libroRepo = libroRepo;
        this.bibliotecarioRepo = bibliotecarioRepo;
        this.inventarioRepo = inventarioRepo;
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
                        p.isDevuelto(),
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

        Inventario inventario = inventarioRepo.findByLibro(libro)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado para libro"));

        if(inventario.getStock() <= 0){
            throw new RuntimeException("No hay ejemplares disponibles para préstamo");
        }

        inventario.setStock(inventario.getStock() - 1);
        inventarioRepo.save(inventario);

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
        nuevo.setDevuelto(false);

        Prestamo guardado = prestamoRepo.save(nuevo);

        return new PrestamoResponseDTO(
                guardado.getIdPrestamo(),
                guardado.getFicha(),
                guardado.getUsuario() != null ? guardado.getUsuario().getIdUsuario() : 0,
                guardado.getLibro() != null ? guardado.getLibro().getIdLibro() : 0,
                guardado.getBibliotecario() != null ? guardado.getBibliotecario().getIdBibliotecario() : 0,
                guardado.isActivo(),
                guardado.isMultado(),
                guardado.isDevuelto(),
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
                        p.isDevuelto(),
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
                    p.setDevuelto(dto.isDevuelto());
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
                            actualizado.isDevuelto(),
                            actualizado.getEstadoPrestamo(),
                            actualizado.getEstadoDevuelto(),
                            actualizado.getFechaInicio(),
                            actualizado.getFechaFin()
                    );
                });
    }

    public PrestamoResponseDTO devolverLibro(Long prestamoId) {
        Prestamo prestamo = prestamoRepo.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
    
        if (prestamo.isDevuelto()) {
            throw new RuntimeException("Este préstamo ya fue marcado como devuelto");
        }
    
        Libro libro = prestamo.getLibro();
        Inventario inventario = inventarioRepo.findByLibro(libro)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
    
        inventario.setStock(inventario.getStock() + 1);
        inventarioRepo.save(inventario);
    
        prestamo.setDevuelto(true);
        Prestamo actualizado = prestamoRepo.save(prestamo);
    
        return new PrestamoResponseDTO(
                actualizado.getIdPrestamo(),
                actualizado.getFicha(),
                actualizado.getUsuario() != null ? actualizado.getUsuario().getIdUsuario() : 0,
                actualizado.getLibro() != null ? actualizado.getLibro().getIdLibro() : 0,
                actualizado.getBibliotecario() != null ? actualizado.getBibliotecario().getIdBibliotecario() : 0,
                actualizado.isActivo(),
                actualizado.isMultado(),
                actualizado.isDevuelto(),
                actualizado.getEstadoPrestamo(),
                actualizado.getEstadoDevuelto(),
                actualizado.getFechaInicio(),
                actualizado.getFechaFin()
        );
    }
    
}
