package com.scriptorium.scriptorium.dto;

import java.time.LocalDate;

public class PrestamoRequestDTO {

    private String ficha;
    private String usuario;
    private Long libroId;
    private String bibliotecario;
    private boolean activo;
    private boolean multado;
    private boolean devuelto;
    private String estadoPrestamo;

    private LocalDate fechaInicio;

    // Getters y Setters

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    public String getUsuarioId() {
        return usuario;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuario = usuarioId;
    }

    public Long getLibroId() {
        return libroId;
    }

    public void setLibroId(Long libroId) {
        this.libroId = libroId;
    }

    public String getBibliotecarioId() {
        return bibliotecario;
    }

    public void setBibliotecarioId(String bibliotecarioId) {
        this.bibliotecario = bibliotecarioId;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isMultado() {
        return multado;
    }

    public void setMultado(boolean multado) {
        this.multado = multado;
    }

    public String getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public void setEstadoPrestamo(String estadoPrestamo) {
        this.estadoPrestamo = estadoPrestamo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }
}
