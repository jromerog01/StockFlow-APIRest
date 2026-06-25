package com.jesus.stockflow.entities.dtos;

public class VentaProductoNombresDTO {

    private String nombreProducto;
    private int cantidad;

    public VentaProductoNombresDTO(String nombreProducto, int cantidad) {
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
}
