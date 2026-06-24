package com.jesus.stockflow.entities.dtos;

import java.math.BigDecimal;

public class ProductoRequestDTO {

    private int idCategoria;
    private int idProveedor;
    private String sku;
    private String nombre;
    private BigDecimal precio;
    private int stock;

    public ProductoRequestDTO(int idCategoria, int idProveedor, String sku, String nombre, BigDecimal precio, int stock) {
        this.idCategoria = idCategoria;
        this.idProveedor = idProveedor;
        this.sku = sku;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public ProductoRequestDTO() {
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
