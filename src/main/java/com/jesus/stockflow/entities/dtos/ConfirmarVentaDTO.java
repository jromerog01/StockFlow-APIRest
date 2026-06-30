package com.jesus.stockflow.entities.dtos;

import com.jesus.stockflow.entities.enums.MetodoPago;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ConfirmarVentaDTO {

    private int idVenta;
    private MetodoPago metodoPago;
    private List<VentaProductoNombresDTO> productos;
    private BigDecimal subtotal;
    private BigDecimal total;

    public ConfirmarVentaDTO(MetodoPago metodoPago, List<VentaProductoNombresDTO> productos) {
        this.metodoPago = metodoPago;
        this.productos = new ArrayList<>(productos);
    }

    public ConfirmarVentaDTO(int idVenta, MetodoPago metodoPago, List<VentaProductoNombresDTO> productos, BigDecimal subtotal, BigDecimal total) {
        this.metodoPago = metodoPago;
        this.productos = productos;
        this.subtotal = subtotal;
        this.total = total;
        this.idVenta = idVenta;
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

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }
}
