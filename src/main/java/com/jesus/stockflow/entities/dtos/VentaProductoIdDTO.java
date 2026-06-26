package com.jesus.stockflow.entities.dtos;

// Agregar un contructor para poder eliminar VentaProductoUpdateCantidadDTO
public class VentaProductoIdDTO {

    private int idProducto;
    private int cantidad;

    public VentaProductoIdDTO(int idProducto, int cantidad) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public VentaProductoIdDTO() {
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
