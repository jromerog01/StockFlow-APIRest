package com.jesus.stockflow.entities.dtos;

import com.jesus.stockflow.entities.enums.MetodoPago;

import java.util.List;

public class ConfirmarVentaDTO {

    private MetodoPago metodoPago;
    private List<VentaProductoNombresDTO> productos;

    public ConfirmarVentaDTO(MetodoPago metodoPago, List<VentaProductoNombresDTO> productos) {
        this.metodoPago = metodoPago;
        this.productos = productos;
    }

    public ConfirmarVentaDTO() {
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public List<VentaProductoNombresDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<VentaProductoNombresDTO> productos) {
        this.productos = productos;
    }
}
