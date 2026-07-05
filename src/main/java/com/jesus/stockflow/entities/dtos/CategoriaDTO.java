package com.jesus.stockflow.entities.dtos;

public class CategoriaDTO {
    
    private int idCategoria;
    private String nombre;

    public CategoriaDTO(String nombre){
        this.nombre = nombre;
    }

    public CategoriaDTO(int idCategoria, String nombre) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
    }

    public CategoriaDTO() {
    }

    public int getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    

}
