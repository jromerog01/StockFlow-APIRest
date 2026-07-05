package com.jesus.stockflow.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_categoria_seq")
    @SequenceGenerator(name = "id_categoria_seq", sequenceName = "id_categoria_seq", allocationSize = 1)
    @Column(name = "id_categoria")
    private int idCategoria;

    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public Categoria() {
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int id_categoria) {
        this.idCategoria = id_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
