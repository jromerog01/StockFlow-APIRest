package com.jesus.stockflow.entities.dtos;


import com.jesus.stockflow.entities.enums.TipoMovimiento;

public class MovimientoInventarioDTO {

    private int idProducto;
    private TipoMovimiento tipoMovimiento;
    private int cantidad;

    public MovimientoInventarioDTO(int idProducto, TipoMovimiento tipoMovimiento, int cantidad) {
        this.idProducto = idProducto;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
    }

    public MovimientoInventarioDTO() {
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
