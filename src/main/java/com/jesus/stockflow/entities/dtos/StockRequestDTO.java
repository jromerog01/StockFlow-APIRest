package com.jesus.stockflow.entities.dtos;

public class StockRequestDTO {

    private int cantidad;

    public StockRequestDTO(int cantidad) {
        this.cantidad = cantidad;
    }

    public StockRequestDTO() {
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
