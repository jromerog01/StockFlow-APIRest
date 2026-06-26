package com.jesus.stockflow.entities.dtos;

public class VentaProductoNombresDTO {

    private int idProducto;
    private String sku;
    private String nombreProducto;
    private int cantidad;

    public VentaProductoNombresDTO(int idProducto, String sku, String nombreProducto, int cantidad) {
        this.idProducto = idProducto;
        this.sku = sku;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
    }

    public VentaProductoNombresDTO() {
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
}
