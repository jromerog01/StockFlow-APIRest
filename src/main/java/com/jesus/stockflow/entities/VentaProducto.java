package com.jesus.stockflow.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "venta_producto")
public class VentaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_venta_producto_seq")
    @SequenceGenerator(name = "id_venta_producto_seq", sequenceName = "id_venta_producto_seq", allocationSize = 1)
    @Column(name = "id_venta_producto")
    private int idVentaProducto;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_venta")
    private Venta venta;

    private int unidades;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    private BigDecimal subtotal;

    public VentaProducto(Producto producto, Venta venta, int unidades, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.producto = producto;
        this.venta = venta;
        this.unidades = unidades;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public VentaProducto() {
    }

    public int getIdVentaProducto() {
        return idVentaProducto;
    }

    public void setIdVentaProducto(int idVentaProducto) {
        this.idVentaProducto = idVentaProducto;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
