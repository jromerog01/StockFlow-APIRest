package com.jesus.stockflow.entities.dtos;

import java.math.BigDecimal;

public class ProductoUpdateRequestDTO {

    private int idCategoria;
    private int idProveedor;
    private String sku;
    private String nombre;
    private BigDecimal precio;

    public ProductoUpdateRequestDTO(int idCategoria, int idProveedor, String sku, String nombre, BigDecimal precio) {
        this.idCategoria = idCategoria;
        this.idProveedor = idProveedor;
        this.sku = sku;
        this.nombre = nombre;
        this.precio = precio;
    }

    public ProductoUpdateRequestDTO() {
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
