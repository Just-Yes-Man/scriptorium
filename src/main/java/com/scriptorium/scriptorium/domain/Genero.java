package com.scriptorium.scriptorium.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idGenero;
    private String descripcion;
    
    public long getIdGenero() {
        return idGenero;
    }
    public void setIdGenero(long idGenero) {
        this.idGenero = idGenero;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
