package com.jesus.stockflow.entities.dtos;

import java.math.BigDecimal;

public class ProductoResponseDTO {

    private int id;
    private String categoria;
    private String proveedor;
    private String sku;
    private String nombre;
    private BigDecimal precio;
    private int stock;
    private boolean activo;

    public ProductoResponseDTO(int id, String categoria, String proveedor, String sku, String nombre, BigDecimal precio, int stock, boolean activo) {
        this.id = id;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.sku = sku;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.activo = activo;
    }

    public ProductoResponseDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
