package com.scriptorium.scriptorium.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TipoMulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoMulta;

    private String tipo;
    private String descripcion;

    public Long getIdTipoMulta() {
        return idTipoMulta;
    }

    public void setIdTipoMulta(Long idTipoMulta) {
        this.idTipoMulta = idTipoMulta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
