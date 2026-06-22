package com.jesus.stockflow.entities.dtos;

public class CategoriaDTO {

    private String nombre;

    public CategoriaDTO(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
