package com.jesus.stockflow.entities.dtos;

public class VentaProductoUpdateCantidadDTO {

    private int cantidad;

    public VentaProductoUpdateCantidadDTO(int cantidad) {
        this.cantidad = cantidad;
    }

    public VentaProductoUpdateCantidadDTO() {
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
