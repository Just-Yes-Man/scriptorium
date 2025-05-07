package com.scriptorium.scriptorium.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Bibliotecario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBibliotecario;
    @Column(length = 100)
    private String usuario;
    @Column(length = 200)
    private String contraseña;

    public Long getIdBibliotecario() {
        return idBibliotecario;
    }

    public void setIdBibliotecario(Long idBibliotecario) {
        this.idBibliotecario = idBibliotecario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

}
