package com.scriptorium.scriptorium.dto;

import java.time.LocalDate;

public class UsuarioResponseDTO {
    private long id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String contacto;
    private String fotografia;
    private String clave;
    
    public UsuarioResponseDTO() {}

    public UsuarioResponseDTO(long id, String nombre, LocalDate fechaNacimiento, String direccion, String contacto, String fotografia,
            String clave) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.contacto = contacto;
        this.fotografia = fotografia;
        this.clave = clave;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    

    
}
