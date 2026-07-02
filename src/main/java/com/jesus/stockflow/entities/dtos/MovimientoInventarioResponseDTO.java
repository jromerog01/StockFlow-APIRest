package com.jesus.stockflow.entities.dtos;

import com.jesus.stockflow.entities.enums.TipoMovimiento;

import java.time.LocalDateTime;

public class MovimientoInventarioResponseDTO {

    private int idMovimiento;
    private String nombreProducto;
    private String sku;
    private int cantidad;
    private TipoMovimiento tipoMovimiento;
    private LocalDateTime fecha;

    public MovimientoInventarioResponseDTO(int idMovimiento, String nombreProducto, String sku, int cantidad, TipoMovimiento tipoMovimiento, LocalDateTime fecha) {
        this.idMovimiento = idMovimiento;
        this.nombreProducto = nombreProducto;
        this.sku = sku;
        this.cantidad = cantidad;
        this.tipoMovimiento = tipoMovimiento;
        this.fecha = fecha;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }
}
